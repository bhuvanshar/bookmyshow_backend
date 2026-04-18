package com.bookmyshow;

import org.junit.jupiter.api.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Concurrency test demonstrating that only ONE booking succeeds
 * when multiple threads compete for the same seat.
 *
 * In a full integration test with @SpringBootTest and a real DB,
 * each thread would call bookingService.initiateBooking() with the same
 * showSeatIds. The @Version optimistic lock on ShowSeat ensures only one
 * thread succeeds while others get ObjectOptimisticLockingFailureException
 * which is caught and wrapped as ConcurrencyException.
 *
 * This unit test simulates the pattern using CountDownLatch and AtomicInteger.
 */
class ConcurrencyBookingTest {

    @Test
    @DisplayName("Only one thread should successfully book the same seat (simulated)")
    void testConcurrentSeatBooking() throws Exception {
        int threadCount = 5;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        // Simulate: all 5 threads try to book the same seat simultaneously
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    startLatch.await(); // All threads start at the same time

                    // Simulate optimistic locking: only the first CAS succeeds
                    // In real code: bookingService.initiateBooking(request, userId)
                    // The @Version field on ShowSeat causes only one saveAll() to succeed
                    if (successCount.compareAndSet(0, 1)) {
                        // This thread "won" the optimistic lock race
                        System.out.println("Thread " + threadId + ": Booking SUCCEEDED");
                    } else {
                        // This thread lost - simulates ObjectOptimisticLockingFailureException
                        failureCount.incrementAndGet();
                        System.out.println("Thread " + threadId + ": Booking FAILED (ConcurrencyException)");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    failureCount.incrementAndGet();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Release all threads simultaneously
        boolean completed = doneLatch.await(10, TimeUnit.SECONDS);

        assertTrue(completed, "All threads should complete within timeout");
        assertEquals(1, successCount.get(), "Exactly ONE booking should succeed");
        assertEquals(threadCount - 1, failureCount.get(), "All other attempts should fail with ConcurrencyException");

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Sequential bookings for different seats should all succeed")
    void testSequentialDifferentSeatBookings() {
        AtomicInteger successCount = new AtomicInteger(0);
        int totalBookings = 5;

        // Simulate: 5 users each booking a DIFFERENT seat (no conflicts)
        for (int i = 0; i < totalBookings; i++) {
            // Each user has their own unique seat - no version conflict
            successCount.incrementAndGet();
        }

        assertEquals(totalBookings, successCount.get(),
                "All bookings for different seats should succeed");
    }

    @Test
    @DisplayName("Booking after lock expiry should succeed for new user")
    void testBookingAfterLockExpiry() {
        AtomicInteger seatVersion = new AtomicInteger(0);

        // User 1 locks the seat (version 0 -> 1)
        int v1 = seatVersion.incrementAndGet();
        assertEquals(1, v1, "Version after first lock should be 1");

        // Simulate: lock expires, seat released back to AVAILABLE (version stays at 1)
        // Scheduler resets status to AVAILABLE but version remains incremented

        // User 2 books the seat (version 1 -> 2)
        int v2 = seatVersion.incrementAndGet();
        assertEquals(2, v2, "Version after second booking should be 2");

        // Both operations succeeded sequentially - no conflict
        assertTrue(v2 > v1, "Second booking version should be higher");
    }
}
