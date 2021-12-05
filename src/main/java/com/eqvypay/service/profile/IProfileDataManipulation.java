package com.eqvypay.service.profile;

import com.eqvypay.persistence.User;

public interface IProfileDataManipulation {
    public void getProfile(User user) throws Exception;
}
