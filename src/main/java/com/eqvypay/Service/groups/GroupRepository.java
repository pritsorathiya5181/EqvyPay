package com.eqvypay.Service.groups;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository {
    public void deleteGroup(String group_name, User user) throws Exception;

    public void joinGroup(User user, String inputId) throws Exception;

    public void createGroup(Group group) throws Exception;

    public void leaveGroup(User user) throws Exception;
}
