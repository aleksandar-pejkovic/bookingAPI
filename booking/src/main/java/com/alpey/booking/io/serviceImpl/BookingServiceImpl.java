package com.alpey.booking.io.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.booking.io.repository.BookingRepository;
import com.alpey.booking.io.repository.HotelRepository;
import com.alpey.booking.io.repository.UserRepository;
import com.alpey.booking.io.service.BookingService;
import com.alpey.booking.model.dto.BookingDto;
import com.alpey.booking.model.entity.BookingEntity;
import com.alpey.booking.model.entity.HotelEntity;
import com.alpey.booking.model.entity.UserEntity;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	HotelRepository hotelRepository;

	@Override
	public BookingDto createBooking(BookingDto booking) {
		try {
			if (exists(booking.getBookingId())) {
				return new BookingDto();
			}
			UserEntity user = userRepository.findByUsername(booking.getUsername());
			HotelEntity hotel = hotelRepository.findByName(booking.getHotelName());
			if (user != null && hotel != null) {
				BookingEntity newBooking = copyDtoToBookingEntity(booking, user, hotel);
				BookingEntity storedBooking = bookingRepository.save(newBooking);
				BookingDto returnValue = copyBookingEntityToDto(user, hotel, storedBooking);
				return returnValue;
			}
			return new BookingDto();
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new BookingDto();
		}
	}

	@Override
	public BookingDto updateBooking(BookingDto booking) {
		try {
			if (!exists(booking.getBookingId())) {
				return new BookingDto();
			}
			UserEntity user = userRepository.findByUsername(booking.getUsername());
			HotelEntity hotel = hotelRepository.findByName(booking.getHotelName());
			if (user != null && hotel != null) {
				BookingEntity updatedBooking = copyDtoToBookingEntity(booking, user, hotel, booking.getBookingId());
				BookingEntity storedBooking = bookingRepository.save(updatedBooking);
				BookingDto returnValue = copyBookingEntityToDto(user, hotel, storedBooking);
				return returnValue;
			}
			return new BookingDto();
		} catch (EntityNotFoundException | NullPointerException e) {
			e.printStackTrace();
			return new BookingDto();
		}
	}

	@Override
	public BookingDto deleteBooking(String bookingId) {
		try {
			if (!exists(bookingId)) {
				return new BookingDto();
			}
			BookingEntity deletedBooking = bookingRepository.findByBookingId(bookingId);
			bookingRepository.delete(deletedBooking);
			BookingDto returnValue = copyBookingEntityToDto(deletedBooking.getUser(), deletedBooking.getHotel(),
					deletedBooking);
			return returnValue;
		} catch (EntityNotFoundException | NullPointerException e) {
			e.printStackTrace();
			return new BookingDto();
		}
	}

	@Override
	public List<BookingDto> loadAllBookings() {
		List<BookingEntity> bookings = (List<BookingEntity>) bookingRepository.findAll();
		if (bookings == null)
			return new ArrayList<>();
		List<BookingDto> returnValue = new ArrayList<>();
		for (BookingEntity booking : bookings) {
			BookingDto dto = copyBookingEntityToDto(booking.getUser(), booking.getHotel(), booking);
			returnValue.add(dto);
		}
		return returnValue;
	}

	@Override
	public List<BookingDto> loadBookingsByUser(String username) {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null)
			return new ArrayList<>();
		List<BookingEntity> bookings = (List<BookingEntity>) bookingRepository.findByUser(user);
		if (bookings == null)
			return new ArrayList<>();
		List<BookingDto> returnValue = new ArrayList<>();
		for (BookingEntity booking : bookings) {
			BookingDto dto = copyBookingEntityToDto(booking.getUser(), booking.getHotel(), booking);
			returnValue.add(dto);
		}
		return returnValue;
	}

	@Override
	public List<BookingDto> loadBookingsByHotel(String name) {
		HotelEntity hotel = hotelRepository.findByName(name);
		if (hotel == null)
			return new ArrayList<>();
		List<BookingEntity> bookings = (List<BookingEntity>) bookingRepository.findByHotel(hotel);
		if (bookings == null)
			return new ArrayList<>();
		List<BookingDto> returnValue = new ArrayList<>();
		for (BookingEntity booking : bookings) {
			BookingDto dto = copyBookingEntityToDto(booking.getUser(), booking.getHotel(), booking);
			returnValue.add(dto);
		}
		return returnValue;
	}

	private boolean exists(String bookingId) {
		BookingEntity storedBooking = bookingRepository.findByBookingId(bookingId);
		if (storedBooking == null) {
			return false;
		} else {
			return true;
		}
	}

	private BookingDto copyBookingEntityToDto(UserEntity user, HotelEntity hotel, BookingEntity storedBooking) {
		BookingDto returnValue = new BookingDto();
		BeanUtils.copyProperties(storedBooking, returnValue);
		returnValue.setUsername(user.getUsername());
		returnValue.setFirstName(user.getFirstName());
		returnValue.setLastName(user.getLastName());
		returnValue.setEmail(user.getEmail());
		returnValue.setHotelName(hotel.getName());
		return returnValue;
	}

	private BookingEntity copyDtoToBookingEntity(BookingDto bookingDto, UserEntity user, HotelEntity hotel) {
		BookingEntity booking = new BookingEntity();
		BeanUtils.copyProperties(bookingDto, booking);
		booking.setUser(user);
		booking.setHotel(hotel);
		return booking;
	}

	private BookingEntity copyDtoToBookingEntity(BookingDto bookingDto, UserEntity user, HotelEntity hotel,
			String bookingId) {
		BookingEntity booking = bookingRepository.findByBookingId(bookingId);
		BeanUtils.copyProperties(bookingDto, booking);
		booking.setUser(user);
		booking.setHotel(hotel);
		return booking;
	}

}
