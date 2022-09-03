package com.alpey.booking.io.serviceImpl;

import java.util.List;

import com.alpey.booking.model.dto.UserDto;

public interface UserSevice {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user);

	UserDto deleteUser(String username);

	UserDto loadUserByUsername(String username);
	
	UserDto loadUserByEmail(String email);
	
	UserDto loadUserByPhone(String phone);
	
	List<UserDto> loadAllUsers();

}
