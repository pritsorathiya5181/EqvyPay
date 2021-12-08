package com.eqvypay.service.groups;

import com.eqvypay.persistence.IGroup;
import com.eqvypay.persistence.IUser;

import java.util.ArrayList;
import java.util.List;

public interface IGroupDataManipulation {
    public void createTable() throws Exception;

    public void createGroupMembersTable() throws Exception;

    public boolean tableExist(String tableName) throws Exception;

    public List<IGroup> getAllGroups() throws Exception;

    public List<String> getFriendsGroupIds(IUser user) throws Exception;

	ArrayList<IGroup> getAllJoinedGroups(IUser user) throws Exception;

	List<String> getMembersOfGroup(String groupId) throws Exception;
}
