package com.alpey.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {

	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String city;
	private String phone;
	
}
