package com.eqvypay.service.groups;

import com.eqvypay.persistence.IGroup;
import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository {
    public void deleteGroup(String group_name, IUser user) throws Exception;

    public void joinGroup(IUser user, String inputId) throws Exception;

    public void leaveGroup(IUser user) throws Exception;

	void createGroup(IUser user, IGroup group) throws Exception;
	
}
