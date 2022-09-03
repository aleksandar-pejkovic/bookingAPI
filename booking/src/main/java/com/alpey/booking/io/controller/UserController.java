package com.alpey.booking.io.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpey.booking.io.service.UserServiceImpl;
import com.alpey.booking.model.dto.UserDto;
import com.alpey.booking.model.request.UserRequest;
import com.alpey.booking.model.response.UserResponse;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		UserDto newUser = new UserDto();
		BeanUtils.copyProperties(userRequest, newUser);

		UserDto storedUser = userService.createUser(newUser);

		UserResponse returnValue = new UserResponse();
		BeanUtils.copyProperties(storedUser, returnValue);

		return returnValue;
	}

}
