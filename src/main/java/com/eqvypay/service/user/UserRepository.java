package com.eqvypay.service.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.eqvypay.persistence.User;

@Repository
public interface UserRepository {
	public void save(User user) throws Exception;
	public void delete(UUID userId) throws Exception;
}
