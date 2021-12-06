package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;

import java.util.ArrayList;
import java.util.List;

public interface IGroupDataManipulation {
    public void createTable() throws Exception;

    public void createGroupMembersTable() throws Exception;
}
