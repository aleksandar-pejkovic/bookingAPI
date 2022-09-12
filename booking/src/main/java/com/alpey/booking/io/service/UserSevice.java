package com.alpey.booking.io.service;

import java.util.List;

import com.alpey.booking.model.dto.UserDto;
import com.alpey.booking.model.request.LoginDetails;

public interface UserSevice {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user);

	UserDto deleteUser(String username);

	UserDto loadUserByUsername(String username);
	
	UserDto loadUserByEmail(String email);
	
	UserDto loadUserByPhone(String phone);
	
	List<UserDto> loadAllUsers();
	
	UserDto loginValidation(LoginDetails loginDetails);

}
