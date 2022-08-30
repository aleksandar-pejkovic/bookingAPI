package com.alpey.booking.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.booking.model.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);
	UserEntity findByEmail(String email);
	UserEntity findByPhone(String phone);
	
}
