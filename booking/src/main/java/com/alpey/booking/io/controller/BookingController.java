package com.alpey.booking.io.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpey.booking.io.serviceImpl.BookingServiceImpl;
import com.alpey.booking.model.dto.BookingDto;
import com.alpey.booking.model.request.BookingRequest;
import com.alpey.booking.model.response.BookingResponse;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingServiceImpl bookingService;

	@PostMapping
	public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {
		BookingDto newBooking = new BookingDto();
		BeanUtils.copyProperties(bookingRequest, newBooking);
		BookingDto storedBooking = bookingService.createBooking(newBooking);
		BookingResponse returnValue = new BookingResponse();
		BeanUtils.copyProperties(storedBooking, returnValue);
		return returnValue;
	}

	@PutMapping
	public BookingResponse updateBooking(@RequestBody BookingRequest bookingRequest) {
		BookingDto updatedBooking = new BookingDto();
		BeanUtils.copyProperties(bookingRequest, updatedBooking);
		BookingDto storedBooking = bookingService.updateBooking(updatedBooking);
		BookingResponse returnValue = new BookingResponse();
		BeanUtils.copyProperties(storedBooking, returnValue);
		return returnValue;
	}

	@DeleteMapping("/{bookingId}")
	public BookingResponse deleteBooking(@PathVariable String bookingId) {
		BookingDto deletedBooking = bookingService.deleteBooking(bookingId);
		BookingResponse returnValue = new BookingResponse();
		BeanUtils.copyProperties(deletedBooking, returnValue);
		return returnValue;
	}

	@GetMapping
	public List<BookingResponse> getAllBookings() {
		List<BookingResponse> returnValue = new ArrayList<>();
		List<BookingDto> bookings = bookingService.loadAllBookings();

		for (BookingDto booking : bookings) {
			BookingResponse response = new BookingResponse();
			BeanUtils.copyProperties(booking, response);
			returnValue.add(response);
		}
		return returnValue;
	}

	@GetMapping("/user/{username}")
	public List<BookingResponse> getByUser(@PathVariable String username) {
		List<BookingResponse> returnValue = new ArrayList<>();
		List<BookingDto> bookings = bookingService.loadBookingsByUser(username);

		for (BookingDto booking : bookings) {
			BookingResponse response = new BookingResponse();
			BeanUtils.copyProperties(booking, response);
			returnValue.add(response);
		}
		return returnValue;
	}

	@GetMapping("/hotel/{hotelName}")
	public List<BookingResponse> getByHotel(@PathVariable String hotelName) {
		List<BookingResponse> returnValue = new ArrayList<>();
		List<BookingDto> bookings = bookingService.loadBookingsByHotel(hotelName);

		for (BookingDto booking : bookings) {
			BookingResponse response = new BookingResponse();
			BeanUtils.copyProperties(booking, response);
			returnValue.add(response);
		}
		return returnValue;
	}

	@GetMapping("/{bookingId}")
	public BookingResponse getByBookingId(@PathVariable String bookingId) {
		BookingResponse response = new BookingResponse();
		BookingDto booking = bookingService.loadBookingByBookingId(bookingId);
		BeanUtils.copyProperties(booking, response);
		return response;
	}

}
