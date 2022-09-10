package com.alpey.booking.io.service;

import java.time.LocalDate;
import java.util.List;

import com.alpey.booking.model.dto.HotelDto;

public interface HotelService {
	
	HotelDto createHotel(HotelDto hotel);

	HotelDto updateHotel(HotelDto hotel);

	HotelDto deleteHotel(String name);

	HotelDto loadHotelByName(String name);
	
	List<HotelDto> loadHotelByCity(String city);
	
	List<HotelDto> loadHotelByStars(int stars);
	
	List<HotelDto> loadAllHotels();
	
	List<LocalDate> getBookedDates(HotelDto hotel);

}
