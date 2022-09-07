package com.alpey.booking.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingDto {

	private String bookingId;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String hotelName;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate beginDate;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate endDate;

}
