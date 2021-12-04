package com.eqvypay.Service.user;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.eqvypay.Persistence.User;

@Repository
public interface UserRepository {
	public User getUserByEmailAndPassword(String email,String password) throws Exception;
	public void save(User user) throws Exception;
	public void createTable() throws Exception;
	public void delete(UUID userId) throws Exception;
	public User getByEmail(String email) throws Exception;
	public User getByUuid(UUID uuid) throws Exception;
}
