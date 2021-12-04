package com.eqvypay.Persistence;

import java.util.List;
import java.util.Random;

public class Group {
    private String groupId;
    private String groupName;
    private String groupDesc;

    public Group() {
        // generate random group id
        Random random = new Random();
        this.groupId = String.valueOf(random.nextInt(10)) + String.valueOf(random.nextInt(10)) + (char) (random.nextInt(26) + 'a') + (char) (random.nextInt(26) + 'a') + String.valueOf(random.nextInt(10) + String.valueOf(random.nextInt(10)));
        this.groupId = this.groupId.toUpperCase();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
}
