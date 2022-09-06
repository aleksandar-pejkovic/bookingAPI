package com.alpey.booking.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.booking.model.entity.BookingEntity;
import com.alpey.booking.model.entity.HotelEntity;
import com.alpey.booking.model.entity.UserEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

	BookingEntity findByBookingId(String bookingId);
	List<BookingEntity> findByUser(UserEntity user);
	List<BookingEntity> findByHotel(HotelEntity hotel);
}
