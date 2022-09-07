package com.alpey.booking.io.service;

import java.util.List;

import com.alpey.booking.model.dto.BookingDto;

public interface BookingService {
	
	BookingDto createBooking(BookingDto booking);
	
	BookingDto updateBooking(BookingDto booking);
	
	BookingDto deleteBooking(String bookingId);
	
	List<BookingDto> loadAllBookings();
	
	List<BookingDto> loadBookingsByUser(String username);
	
	List<BookingDto> loadBookingsByHotel(String name);
	
	BookingDto loadBookingByBookingId(String bookingId);

}
