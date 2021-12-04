package com.eqvypay.Service.profile;

import org.springframework.stereotype.Repository;
import com.eqvypay.Persistence.User;

@Repository
public interface ProfileRepository {
    public void updateUsername(User user, String username) throws Exception;

    public void updateContact(User user, String contact) throws Exception;

    public void updatePassword(User user, String password) throws Exception;
}
