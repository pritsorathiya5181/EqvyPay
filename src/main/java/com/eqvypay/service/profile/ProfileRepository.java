package com.eqvypay.service.profile;

import org.springframework.stereotype.Repository;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;

@Repository
public interface ProfileRepository {
    public void updateUsername(IUser user, String username) throws Exception;

    public void updateContact(IUser user, String contact) throws Exception;

    public void updatePassword(IUser user, String password) throws Exception;
}
