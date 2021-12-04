package com.eqvypay.Service.user;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.eqvypay.Persistence.User;

@Repository
public interface UserRepository {
	public void save(User user) throws Exception;
	public void delete(UUID userId) throws Exception;
}
