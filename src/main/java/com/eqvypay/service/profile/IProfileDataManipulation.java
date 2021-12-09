package com.eqvypay.service.profile;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IUser;

@Repository
public interface IProfileDataManipulation {
    public void getProfile(IUser user) throws Exception;
}
