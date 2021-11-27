package com.eqvypay.Service;

import com.eqvypay.Persistence.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository {
    public void createGroupTable() throws Exception;
    public void deleteGroupTable() throws Exception;
    public Group getGroupById(String groupId) throws Exception;
    public void addGroupMember(Group group) throws Exception;
    public boolean tableExist(String groupName) throws Exception;
    public void save(Group group) throws Exception;
}
