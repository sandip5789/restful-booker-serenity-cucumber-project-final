package com.restful.booker.model;

import java.util.HashMap;

public class BookingDates {
    public HashMap<String, Object> getBookingDates()
    {
        HashMap<String, Object> bookingDates = new HashMap<>();                                   //code reusability
        bookingDates.put("checkin", "2024-03-01");
        bookingDates.put("checkout", "2025-05-05");
        return bookingDates;                                                                   //avoid code duplicacy
    }
}
