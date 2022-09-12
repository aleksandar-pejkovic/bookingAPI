package com.alpey.booking.io.controller;

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

import com.alpey.booking.io.serviceImpl.UserServiceImpl;
import com.alpey.booking.model.dto.UserDto;
import com.alpey.booking.model.request.LoginDetails;
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

	@PutMapping
	public UserResponse updateUser(@RequestBody UserRequest userRequest) {
		UserDto storedUser = new UserDto();
		BeanUtils.copyProperties(userRequest, storedUser);

		UserDto updatedUser = userService.updateUser(storedUser);

		UserResponse returnValue = new UserResponse();
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@DeleteMapping("/{username}")
	public UserResponse deleteUser(@PathVariable String username) {
		UserDto deletedUser = userService.deleteUser(username);

		UserResponse returnValue = new UserResponse();
		BeanUtils.copyProperties(deletedUser, returnValue);

		return returnValue;
	}

	@GetMapping
	public List<UserResponse> getAllUsers() {
		List<UserResponse> returnValue = new ArrayList<>();
		List<UserDto> users = userService.loadAllUsers();

		for (UserDto user : users) {
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(user, userResponse);
			returnValue.add(userResponse);
		}

		return returnValue;
	}

	@GetMapping("/username/{username}")
	public UserResponse getUserByUsername(@PathVariable String username) {
		UserResponse returnValue = new UserResponse();
		UserDto storedUser = userService.loadUserByUsername(username);
		BeanUtils.copyProperties(storedUser, returnValue);
		return returnValue;
	}

	@GetMapping("/email/{email}")
	public UserResponse getUserByEmail(@PathVariable String email) {
		UserResponse returnValue = new UserResponse();
		UserDto storedUser = userService.loadUserByEmail(email);
		BeanUtils.copyProperties(storedUser, returnValue);
		return returnValue;
	}

	@GetMapping("/phone/{phone}")
	public UserResponse getUserByPhone(@PathVariable String phone) {
		UserResponse returnValue = new UserResponse();
		UserDto storedUser = userService.loadUserByPhone(phone);
		BeanUtils.copyProperties(storedUser, returnValue);
		return returnValue;
	}

	@GetMapping("/login")
	public UserResponse login(@RequestBody LoginDetails loginDetails) {
		UserDto dto = userService.loginValidation(loginDetails);
		UserResponse returnValue = new UserResponse(); 
		BeanUtils.copyProperties(dto, returnValue);
		return returnValue;
	}

}
