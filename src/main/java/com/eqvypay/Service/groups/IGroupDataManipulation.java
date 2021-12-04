package com.eqvypay.Service.groups;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;

import java.util.List;

public interface IGroupDataManipulation {
    public void createTable() throws Exception;

    public void createGroupMembersTable() throws Exception;

    public boolean tableExist(String tableName) throws Exception;

    public List<Group> getAllGroups() throws Exception;

    public List<String> getFriendsGroupIds(User user) throws Exception;
}
