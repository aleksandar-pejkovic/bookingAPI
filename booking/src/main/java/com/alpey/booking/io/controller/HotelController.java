package com.alpey.booking.io.controller;

import java.time.LocalDate;
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

import com.alpey.booking.io.serviceImpl.HotelServiceImpl;
import com.alpey.booking.model.dto.HotelDto;
import com.alpey.booking.model.request.HotelRequest;
import com.alpey.booking.model.response.HotelResponse;

@RestController
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	HotelServiceImpl hotelService;

	@PostMapping
	public HotelResponse createHotel(@RequestBody HotelRequest hotelRequest) {
		HotelDto newHotel = new HotelDto();
		BeanUtils.copyProperties(hotelRequest, newHotel);

		HotelDto storedHotel = hotelService.createHotel(newHotel);

		HotelResponse returnValue = new HotelResponse();
		BeanUtils.copyProperties(storedHotel, returnValue);

		return returnValue;
	}

	@PutMapping
	public HotelResponse updateHotel(@RequestBody HotelRequest hotelRequest) {
		HotelDto storedHotel = new HotelDto();
		BeanUtils.copyProperties(hotelRequest, storedHotel);

		HotelDto updatedHotel = hotelService.updateHotel(storedHotel);

		HotelResponse returnValue = new HotelResponse();
		BeanUtils.copyProperties(updatedHotel, returnValue);

		return returnValue;
	}

	@DeleteMapping("/{name}")
	public HotelResponse deleteHotel(@PathVariable String name) {
		HotelDto deletedHotel = hotelService.deleteHotel(name);

		HotelResponse returnValue = new HotelResponse();
		BeanUtils.copyProperties(deletedHotel, returnValue);

		return returnValue;
	}

	@GetMapping
	public List<HotelResponse> getAllHotels() {
		List<HotelResponse> returnValue = new ArrayList<>();
		List<HotelDto> hotels = hotelService.loadAllHotels();

		for (HotelDto hotel : hotels) {
			HotelResponse hotelResponse = new HotelResponse();
			BeanUtils.copyProperties(hotel, hotelResponse);
			returnValue.add(hotelResponse);
		}

		return returnValue;
	}

	@GetMapping("/name/{name}")
	public HotelResponse getHotelByName(@PathVariable String name) {
		HotelResponse returnValue = new HotelResponse();
		HotelDto storedHotel = hotelService.loadHotelByName(name);
		BeanUtils.copyProperties(storedHotel, returnValue);
		return returnValue;
	}

	@GetMapping("/city/{city}")
	public List<HotelResponse> getHotelByCity(@PathVariable String city) {
		List<HotelResponse> returnValue = new ArrayList<>();
		List<HotelDto> storedHotels = hotelService.loadHotelByCity(city);
		for (HotelDto hotelDto : storedHotels) {
			HotelResponse hotelResponse = new HotelResponse();
			BeanUtils.copyProperties(hotelDto, hotelResponse);
			returnValue.add(hotelResponse);
		}
		return returnValue;
	}

	@GetMapping("/stars/{stars}")
	public List<HotelResponse> getHotelByStars(@PathVariable int stars) {
		List<HotelResponse> returnValue = new ArrayList<>();
		List<HotelDto> storedHotels = hotelService.loadHotelByStars(stars);
		for (HotelDto hotelDto : storedHotels) {
			HotelResponse hotelResponse = new HotelResponse();
			BeanUtils.copyProperties(hotelDto, hotelResponse);
			returnValue.add(hotelResponse);
		}
		return returnValue;
	}

	@GetMapping("/dates/{hotelName}")
	public List<LocalDate> getBookedDates(@PathVariable String hotelName) {
		HotelDto hotel = hotelService.loadHotelByName(hotelName);
		return hotelService.getBookedDates(hotel);
	}

}
