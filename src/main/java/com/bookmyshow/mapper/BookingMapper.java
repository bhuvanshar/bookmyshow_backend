package com.bookmyshow.mapper;

import com.bookmyshow.dto.response.BookedSeatInfo;
import com.bookmyshow.dto.response.BookingResponse;
import com.bookmyshow.entity.Booking;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        if (booking == null) {
            return null;
        }

        String movieTitle = null;
        LocalDate showDate = null;
        LocalTime startTime = null;
        if (booking.getShow() != null) {
            if (booking.getShow().getMovie() != null) {
                movieTitle = booking.getShow().getMovie().getTitle();
            }
            showDate = booking.getShow().getShowDate();
            startTime = booking.getShow().getStartTime();
        }

        String theatreName = null;
        if (booking.getShow() != null && booking.getShow().getScreen() != null
                && booking.getShow().getScreen().getTheatre() != null) {
            theatreName = booking.getShow().getScreen().getTheatre().getName();
        }

        String screenName = null;
        if (booking.getShow() != null && booking.getShow().getScreen() != null) {
            screenName = booking.getShow().getScreen().getName();
        }

        List<BookedSeatInfo> bookedSeats = new ArrayList<>();
        if (booking.getBookingSeats() != null && !booking.getBookingSeats().isEmpty()) {
            bookedSeats = booking.getBookingSeats().stream()
                    .map(bookingSeat -> {
                        String rowName = null;
                        Integer seatNumber = null;
                        String seatType = null;

                        if (bookingSeat.getShowSeat() != null && bookingSeat.getShowSeat().getSeat() != null) {
                            rowName = bookingSeat.getShowSeat().getSeat().getRowName();
                            seatNumber = bookingSeat.getShowSeat().getSeat().getSeatNumber();
                            if (bookingSeat.getShowSeat().getSeat().getSeatType() != null) {
                                seatType = bookingSeat.getShowSeat().getSeat().getSeatType().name();
                            }
                        }

                        return BookedSeatInfo.builder()
                                .rowName(rowName)
                                .seatNumber(seatNumber)
                                .seatType(seatType)
                                .price(bookingSeat.getShowSeat() != null ? bookingSeat.getShowSeat().getPrice() : null)
                                .build();
                    })
                    .collect(Collectors.toList());
        }

        String paymentStatus = null;
        if (booking.getPayment() != null && booking.getPayment().getStatus() != null) {
            paymentStatus = booking.getPayment().getStatus().name();
        }

        BookingResponse response = BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .movieTitle(movieTitle)
                .theatreName(theatreName)
                .screenName(screenName)
                .showDate(showDate)
                .startTime(startTime)
                .seats(bookedSeats)
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus() != null ? booking.getStatus().name() : null)
                .bookingTime(booking.getBookingTime())
                .paymentStatus(paymentStatus)
                .build();

        return response;
    }

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    private static final Logger log = LoggerFactory.getLogger(BookingMapper.class);

}
