package com.eqvypay.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.eqvypay.persistence.User;

@Repository
public interface UserRepository {

	public User getUserByEmailAndPassword(String email, String password) throws Exception;

	public User getByEmail(String email) throws Exception;

	public User getByUuid(UUID uuid) throws Exception;

	public List<User> findAllFriends(String userId) throws Exception;
}
