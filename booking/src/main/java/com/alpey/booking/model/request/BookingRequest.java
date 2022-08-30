package com.alpey.booking.model.request;

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
public class BookingRequest {

	private String bookingId;
	private String username;
	private String hotelName;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate beginDate;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate endDate;

}
