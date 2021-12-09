package com.eqvypay.service.user;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.eqvypay.persistence.IUser;

@Repository
public interface UserRepository {

	public IUser getUserByEmailAndPassword(String email, String password) throws Exception;

	public IUser getByEmail(String email) throws Exception;

	public IUser getByUuid(UUID uuid) throws Exception;

	public List<IUser> findAllFriends(String userId) throws Exception;
}
