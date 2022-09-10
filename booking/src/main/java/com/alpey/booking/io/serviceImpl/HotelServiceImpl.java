package com.alpey.booking.io.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.booking.io.repository.BookingRepository;
import com.alpey.booking.io.repository.HotelRepository;
import com.alpey.booking.io.service.HotelService;
import com.alpey.booking.model.dto.HotelDto;
import com.alpey.booking.model.entity.BookingEntity;
import com.alpey.booking.model.entity.HotelEntity;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelRepository hotelRepository;
	@Autowired
	BookingRepository bookingRepository;

	@Override
	public HotelDto createHotel(HotelDto hotel) {

		try {
			if (exists(hotel.getName())) {
				return new HotelDto();
			}
			HotelEntity newHotel = copyDtoToHotelEntity(hotel);
			HotelEntity storedHotel = hotelRepository.save(newHotel);
			HotelDto returnValue = copyHotelEntityToDto(storedHotel);
			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new HotelDto();
		}
	}

	@Override
	public HotelDto updateHotel(HotelDto hotel) {
		String name = hotel.getName();
		try {
			if (!exists(name)) {
				return new HotelDto();
			}
			HotelEntity storedHotel = copyDtoToHotelEntity(hotel, name);
			HotelEntity updatedHotel = hotelRepository.save(storedHotel);
			HotelDto returnValue = copyHotelEntityToDto(updatedHotel);
			return returnValue;
		} catch (EntityNotFoundException | NullPointerException e) {
			e.printStackTrace();
			return new HotelDto();
		}
	}

	@Override
	public HotelDto deleteHotel(String name) {
		try {
			if (!exists(name)) {
				return new HotelDto();
			}
			HotelEntity storedHotel = hotelRepository.findByName(name);
			HotelDto returnValue = copyHotelEntityToDto(storedHotel);

			hotelRepository.delete(storedHotel);
			System.out.println("Hotel " + storedHotel.getName() + " deleted!");
			return returnValue;

		} catch (EntityNotFoundException | NullPointerException e) {
			e.printStackTrace();
			return new HotelDto();
		}
	}

	@Override
	public HotelDto loadHotelByName(String name) {
		try {
			HotelEntity hotelEntity = hotelRepository.findByName(name);
			HotelDto returnValue = new HotelDto();

			if (hotelEntity != null)
				BeanUtils.copyProperties(hotelEntity, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new HotelDto();
		}
	}

	@Override
	public List<HotelDto> loadHotelByCity(String city) {
		try {
			List<HotelDto> returnValue = new ArrayList<>();
			List<HotelEntity> hotels = hotelRepository.findByCity(city);
			if (hotels != null) {
				for (HotelEntity hotel : hotels) {
					HotelDto dto = copyHotelEntityToDto(hotel);
					returnValue.add(dto);
				}
			}
			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<HotelDto> loadHotelByStars(int stars) {
		try {
			List<HotelDto> returnValue = new ArrayList<>();
			List<HotelEntity> hotels = hotelRepository.findByStars(stars);
			if (hotels != null) {
				for (HotelEntity hotel : hotels) {
					HotelDto dto = copyHotelEntityToDto(hotel);
					returnValue.add(dto);
				}
			}
			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<HotelDto> loadAllHotels() {
		List<HotelDto> returnValue = new ArrayList<>();
		List<HotelEntity> hotels = (List<HotelEntity>) hotelRepository.findAll();

		for (HotelEntity hotel : hotels) {
			HotelDto hotelDto = copyHotelEntityToDto(hotel);
			returnValue.add(hotelDto);
		}

		return returnValue;
	}

	private boolean exists(String name) {
		HotelEntity storedHotel = hotelRepository.findByName(name);

		if (storedHotel == null) {
			return false;
		} else {
			return true;
		}
	}

	private HotelDto copyHotelEntityToDto(HotelEntity entity) {
		HotelDto dto = new HotelDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	private HotelEntity copyDtoToHotelEntity(HotelDto dto) {
		HotelEntity entity = new HotelEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	private HotelEntity copyDtoToHotelEntity(HotelDto dto, String name) {
		HotelEntity entity = hotelRepository.findByName(name);
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	@Override
	public List<LocalDate> getBookedDates(HotelDto hotel) {
		List<LocalDate> bookedDates = new ArrayList<>();
		HotelEntity storedHotel = hotelRepository.findByName(hotel.getName());
		if (storedHotel == null) {
			return new ArrayList<>();
		}
		try {
			List<BookingEntity> allBookings = (List<BookingEntity>) bookingRepository.findAll();
			List<BookingEntity> bookings = allBookings.stream()
					.filter(e -> e.getHotel().getName().equals(storedHotel.getName())).collect(Collectors.toList());

			for (BookingEntity booking : bookings) {
				LocalDate beginDate = booking.getBeginDate();
				LocalDate endDate = booking.getEndDate();

				bookedDates.addAll(beginDate.datesUntil(endDate).collect(Collectors.toList()));

			}

			return bookedDates;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}

	}

}
