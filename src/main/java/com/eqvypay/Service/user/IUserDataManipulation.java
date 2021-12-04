package com.eqvypay.Service.user;

import com.eqvypay.Persistence.User;

import java.util.UUID;

public interface IUserDataManipulation {
    public void createTable() throws Exception;

    public User getUserByEmailAndPassword(String email, String password) throws Exception;

    public User getByEmail(String email) throws Exception;

    public User getByUuid(UUID uuid) throws Exception;
}
