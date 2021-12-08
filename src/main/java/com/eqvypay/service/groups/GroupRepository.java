package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GroupRepository {
    public void deleteGroup(String group_name, User user) throws Exception;

    public void joinGroup(User user, String inputId) throws Exception;

    public void createGroup(User user, Group group) throws Exception;

    public void leaveGroup(User user, String groupName) throws Exception;

    public List<Group> getAllGroups() throws Exception;

    public List<String> getFriendsGroupIds(User user) throws Exception;

    ArrayList<Group> getAllJoinedGroups(User user) throws Exception;

     List<String> getMembersOfGroup(String groupId) throws Exception;
	
}
