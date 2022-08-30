package com.alpey.booking.io.serviceImpl;

import com.alpey.booking.model.dto.UserDto;

public interface UserSeviceImpl {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user);

	UserDto deleteUser(String username);

	UserDto readUser(String username);

}
