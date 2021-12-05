package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository {
    public void deleteGroup(String group_name, User user) throws Exception;

    public void joinGroup(User user, String inputId) throws Exception;

    public void createGroup(Group group) throws Exception;

    public void leaveGroup(User user) throws Exception;
	
}
