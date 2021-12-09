package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.IGroup;
import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GroupRepository {
    public void deleteGroup(String group_name, IUser user) throws Exception;

    public void joinGroup(IUser user, String inputId) throws Exception;

    public void createGroup(IUser user, IGroup group) throws Exception;

    public List<String> getJoinedGroups(IUser user) throws Exception;

    public void leaveGroup(IUser user, String groupName) throws Exception;

    public List<IGroup> getAllGroups() throws Exception;

    public List<String> getFriendsGroupIds(IUser user) throws Exception;

    ArrayList<IGroup> getAllJoinedGroups(IUser user) throws Exception;

    List<String> getMembersOfGroup(String groupId) throws Exception;

}
