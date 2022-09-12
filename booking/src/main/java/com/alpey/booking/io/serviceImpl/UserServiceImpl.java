package com.alpey.booking.io.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.booking.io.repository.UserRepository;
import com.alpey.booking.io.service.UserSevice;
import com.alpey.booking.model.dto.UserDto;
import com.alpey.booking.model.entity.UserEntity;
import com.alpey.booking.model.request.LoginDetails;

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
			UserEntity newUser = copyDtoToUserEntity(user);
			UserEntity storedUser = userRepository.save(newUser);
			UserDto returnValue = copyUserEntityToDto(storedUser);
			return returnValue;
		} catch (EntityExistsException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public UserDto updateUser(UserDto user) {
		String username = user.getUsername();
		try {
			if (!exists(username)) {
				return new UserDto();
			}
			UserEntity storedUser = copyDtoToUserEntity(user, username);
			UserEntity updatedUser = userRepository.save(storedUser);
			UserDto returnValue = copyUserEntityToDto(updatedUser);
			return returnValue;
		} catch (EntityNotFoundException | NullPointerException e) {
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
			UserDto returnValue = copyUserEntityToDto(storedUser);

			userRepository.delete(storedUser);
			System.out.println("User " + storedUser.getUsername() + " deleted!");
			return returnValue;
		} catch (EntityNotFoundException | NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

	@Override
	public List<UserDto> loadAllUsers() {
		List<UserDto> returnValue = new ArrayList<>();
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();

		for (UserEntity user : users) {
			UserDto userDto = copyUserEntityToDto(user);
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

	private UserDto copyUserEntityToDto(UserEntity entity) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	private UserEntity copyDtoToUserEntity(UserDto dto) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	private UserEntity copyDtoToUserEntity(UserDto dto, String username) {
		UserEntity entity = userRepository.findByUsername(username);
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	@Override
	public UserDto loginValidation(LoginDetails loginDetails) {
		try {
			UserEntity storedUser = userRepository.findByUsername(loginDetails.getUsername());
			if (storedUser == null)
				return new UserDto();
			if (storedUser.getPassword().equals(loginDetails.getPassword())) {
				UserDto dto = copyUserEntityToDto(storedUser);
				return dto;
			}
			return new UserDto();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new UserDto();
		}
	}

}
