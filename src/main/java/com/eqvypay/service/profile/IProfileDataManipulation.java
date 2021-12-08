package com.eqvypay.service.profile;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;

public interface IProfileDataManipulation {
    public void getProfile(IUser user) throws Exception;
}
