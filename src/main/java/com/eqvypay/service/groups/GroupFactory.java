package com.eqvypay.service.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.IGroup;

@Service
public class GroupFactory {

	private static GroupFactory groupFactory;
	
	@Autowired
	private IGroupDataManipulation groupDataManipulation;
	
	@Autowired
	private GroupRepository groupRepository;
	
	private IGroup group;
	
	public GroupFactory() {
		if(group == null) {
			group = new Group();
		}
	}
	
	public static GroupFactory getInstance() {
		if(groupFactory == null) {
			groupFactory = new GroupFactory();
		}
		return groupFactory;
	}
	
	public IGroupDataManipulation getGroupDataManipulation() {
		return groupDataManipulation;
	}

	public IGroup getGroup() {
		group = new Group();
		return group;
	}

	public GroupRepository getGroupRepository() {
		return groupRepository;
	}

}
