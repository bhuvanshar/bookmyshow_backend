package com.bookmyshow.exception;

public class SeatLockExpiredException extends BookingException {
    public SeatLockExpiredException(String message) { super(message); }
}
