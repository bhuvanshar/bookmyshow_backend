package com.bookmyshow.service;

import com.bookmyshow.dto.request.PaymentRequest;
import com.bookmyshow.dto.response.PaymentResponse;
import com.bookmyshow.entity.*;
import com.bookmyshow.enums.*;
import com.bookmyshow.exception.*;
import com.bookmyshow.mapper.PaymentMapper;
import com.bookmyshow.repository.*;
import com.bookmyshow.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock private PaymentRepository paymentRepository;
    @Mock private BookingRepository bookingRepository;
    @Mock private BookingService bookingService;

    private final PaymentMapper paymentMapper = new PaymentMapper();

    private PaymentServiceImpl paymentService;

    private Booking testBooking;
    private User testUser;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository, bookingRepository, bookingService, paymentMapper);

        testUser = User.builder().id(1L).fullName("John").email("john@test.com").build();

        Movie movie = Movie.builder().id(1L).title("Test Movie").build();
        Screen screen = Screen.builder().id(1L).name("Screen 1").build();
        Show show = Show.builder().id(1L).movie(movie).screen(screen)
                .showDate(LocalDate.now()).startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0)).basePrice(BigDecimal.valueOf(200))
                .status(ShowStatus.SCHEDULED).build();

        testBooking = Booking.builder()
                .id(1L).user(testUser).show(show)
                .status(BookingStatus.PENDING)
                .bookingReference("TEST1234")
                .totalAmount(BigDecimal.valueOf(400))
                .bookingTime(LocalDateTime.now())
                .bookingSeats(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should process payment successfully")
    void testProcessPaymentSuccess() {
        PaymentRequest request = PaymentRequest.builder()
                .bookingId(1L).paymentMethod("Credit Card").simulateFailure(false).build();

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> {
            Payment p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        PaymentResponse response = paymentService.processPayment(request, 1L);

        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getTransactionId());
        assertEquals("Credit Card", response.getPaymentMethod());
        verify(bookingService).confirmBooking(1L, 1L);
    }

    @Test
    @DisplayName("Should fail payment when simulateFailure is true")
    void testProcessPaymentSimulateFailure() {
        PaymentRequest request = PaymentRequest.builder()
                .bookingId(1L).paymentMethod("UPI").simulateFailure(true).build();

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testBooking));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(inv -> {
            Payment p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        assertThrows(PaymentFailedException.class,
                () -> paymentService.processPayment(request, 1L));
        verify(bookingService).cancelBooking(1L, 1L);
    }

    @Test
    @DisplayName("Should throw when booking is not PENDING")
    void testProcessPaymentForConfirmedBooking() {
        testBooking.setStatus(BookingStatus.CONFIRMED);
        PaymentRequest request = PaymentRequest.builder()
                .bookingId(1L).paymentMethod("Credit Card").simulateFailure(false).build();

        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(testBooking));

        assertThrows(BookingException.class,
                () -> paymentService.processPayment(request, 1L));
    }

    @Test
    @DisplayName("Should throw when booking not found")
    void testProcessPaymentBookingNotFound() {
        PaymentRequest request = PaymentRequest.builder()
                .bookingId(999L).paymentMethod("Credit Card").build();

        when(bookingRepository.findByIdAndUserId(999L, 1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> paymentService.processPayment(request, 1L));
    }

    @Test
    @DisplayName("Should get payment by booking ID")
    void testGetPaymentByBooking() {
        Payment payment = Payment.builder()
                .id(1L).booking(testBooking).amount(BigDecimal.valueOf(400))
                .paymentMethod("UPI").transactionId("TXN-ABC123")
                .status(PaymentStatus.SUCCESS).paymentTime(LocalDateTime.now())
                .build();

        when(paymentRepository.findByBookingId(1L)).thenReturn(Optional.of(payment));

        PaymentResponse response = paymentService.getPaymentByBooking(1L);

        assertNotNull(response);
        assertEquals("TXN-ABC123", response.getTransactionId());
        assertEquals("SUCCESS", response.getStatus());
    }
}
