package com.eqvypay.service.user;

import com.eqvypay.persistence.IUser;

import java.util.UUID;

public interface IUserDataManipulation {
    public void createTable() throws Exception;

    public void save(IUser user) throws Exception;

    public void delete(UUID userId) throws Exception;
}
