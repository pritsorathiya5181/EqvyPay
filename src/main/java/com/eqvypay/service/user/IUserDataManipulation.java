package com.eqvypay.service.user;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;

import java.util.List;
import java.util.UUID;

public interface IUserDataManipulation {
    public void createTable() throws Exception;

    public IUser getUserByEmailAndPassword(String email, String password) throws Exception;

    public IUser getByEmail(String email) throws Exception;

    public IUser getByUuid(UUID uuid) throws Exception;

	public List<IUser> findAllFriends(String userId) throws Exception;
}
