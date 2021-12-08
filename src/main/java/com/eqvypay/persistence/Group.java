package com.eqvypay.persistence;

import java.util.Random;

public class Group implements IGroup {
    private String groupId;
    private String groupName;
    private String groupDesc;

    public Group() {
        // generate random group id
        Random random = new Random();
        this.groupId = String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + (char) (random.nextInt(26) + 'a') + (char) (random.nextInt(26) + 'a') + String.valueOf(random.nextInt(10) + String.valueOf(random.nextInt(10)));
        this.groupId = this.groupId.toUpperCase();
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupDesc() {
        return groupDesc;
    }

    @Override
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
}
