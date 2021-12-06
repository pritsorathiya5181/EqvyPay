package com.eqvypay.service.user;

import com.eqvypay.persistence.User;

import java.util.UUID;

public interface IUserDataManipulation {
    public void createTable() throws Exception;

    public void save(User user) throws Exception;

    public void delete(UUID userId) throws Exception;
}
