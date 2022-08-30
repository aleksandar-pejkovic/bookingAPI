package com.alpey.booking.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.booking.model.entity.BookingEntity;

@Repository
public interface BookingRepository extends CrudRepository<BookingEntity, Long> {

	BookingEntity findByBookingId(String bookingId);
	
}
