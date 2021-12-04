package com.eqvypay.Service;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository {
    public void createGroupTable() throws Exception;
    public void deleteGroup(String group_name, User user) throws Exception;
    public void addGroupMember(User user, String inputId) throws Exception;
    public boolean tableExist(String groupName) throws Exception;
    public void save(Group group) throws Exception;
    public void createGroupMembersTable() throws Exception;
    public void removeGroupMember(User user) throws Exception;
    public List<Group> getAllGroups() throws Exception;
    public List<String> getFriendsGroupIds(User user) throws Exception;
}
