package com.alpey.booking.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.booking.model.entity.HotelEntity;

@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Long>{

	HotelEntity findByName(String name);
	List<HotelEntity> findByCity(String city);
	List<HotelEntity> findByStars(int stars);
	
}
