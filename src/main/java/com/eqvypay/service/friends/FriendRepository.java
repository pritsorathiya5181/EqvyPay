package com.eqvypay.service.friends;

import org.springframework.stereotype.Repository;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;

@Repository
public interface FriendRepository {

    public void addFriendByEmail(IUser user, String email) throws Exception;

    public void addFriendByContact(IUser user, String contact) throws Exception;

    public void removeFriendByEmail(IUser user, String email) throws Exception;

    public void removeFriendByContact(IUser user, String contact) throws Exception;
}
