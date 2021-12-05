package com.eqvypay.service.repository;

import org.springframework.stereotype.Repository;
import com.eqvypay.persistence.User;

@Repository
public interface ProfileRepository extends IRepositories {
    public void updateUsername(User user, String username) throws Exception;

    public void updateContact(User user, String contact) throws Exception;

    public void updatePassword(User user, String password) throws Exception;
}
