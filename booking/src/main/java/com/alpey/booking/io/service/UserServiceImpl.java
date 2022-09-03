package com.alpey.booking.io.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.booking.io.repository.UserRepository;
import com.alpey.booking.io.serviceImpl.UserSevice;
import com.alpey.booking.model.dto.UserDto;
import com.alpey.booking.model.entity.UserEntity;

@Service
public class UserServiceImpl implements UserSevice {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto user) {

		try {
			if (exists(user)) {
				return new UserDto();
			}

			UserEntity newUser = new UserEntity();
			BeanUtils.copyProperties(user, newUser);

			UserEntity storedUser = userRepository.save(newUser);
			UserDto returnValue = new UserDto();
			BeanUtils.copyProperties(storedUser, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto updateUser(UserDto user) {
		try {
			if (!exists(user.getUsername())) {
				return new UserDto();
			}
			UserEntity storedUser = userRepository.findByUsername(user.getUsername());
			BeanUtils.copyProperties(user, storedUser, "id");

			UserEntity updatedUser = userRepository.save(storedUser);
			UserDto returnValue = new UserDto();
			BeanUtils.copyProperties(updatedUser, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto deleteUser(String username) {
		try {
			if (!exists(username)) {
				return new UserDto();
			}

			UserEntity storedUser = userRepository.findByUsername(username);
			UserDto returnValue = new UserDto();
			BeanUtils.copyProperties(storedUser, returnValue);

			userRepository.delete(storedUser);
			System.out.println();
			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public List<UserDto> loadAllUsers() {
		List<UserDto> returnValue = new ArrayList<>();
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

		for (UserEntity user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
	}

	@Override
	public UserDto loadUserByUsername(String username) {
		try {
			UserEntity storedUser = userRepository.findByUsername(username);
			UserDto returnValue = new UserDto();

			if (storedUser != null)
				BeanUtils.copyProperties(storedUser, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto loadUserByEmail(String email) {
		try {
			UserEntity storedUser = userRepository.findByEmail(email);
			UserDto returnValue = new UserDto();

			if (storedUser != null)
				BeanUtils.copyProperties(storedUser, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto loadUserByPhone(String phone) {
		try {
			UserEntity storedUser = userRepository.findByPhone(phone);
			UserDto returnValue = new UserDto();

			if (storedUser != null)
				BeanUtils.copyProperties(storedUser, returnValue);

			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	private boolean exists(String username) {

		UserEntity storedUser = userRepository.findByUsername(username);

		if (storedUser == null) {
			System.out.println("User doesn't eixst!");
			return false;
		} else {
			return true;
		}
	}

	private boolean exists(UserDto user) {
		String username = user.getUsername();
		String email = user.getEmail();
		String phone = user.getPhone();

		UserEntity storedUser = userRepository.findByUsername(username);

		if (storedUser == null) {
			return false;
		} else {

			boolean usernameExists = userRepository.findByUsername(username) != null;
			boolean emailExists = userRepository.findByEmail(email) != null;
			boolean phoneExists = userRepository.findByPhone(phone) != null;

			if (usernameExists)
				System.out.printf("User with username %s already exists!%n", username);
			if (emailExists)
				System.out.printf("User with email %s already exists!%n", email);
			if (phoneExists)
				System.out.printf("User with phone number %s already exists!%n", phone);

			return true;
		}
	}

}
