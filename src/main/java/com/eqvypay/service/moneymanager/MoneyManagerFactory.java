package com.eqvypay.service.moneymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IPersonalActivity;
import com.eqvypay.persistence.PersonalActivity;

@Service
public class MoneyManagerFactory {

	private static MoneyManagerFactory moneyManagerFactory = null;
	
	@Autowired
	private IMoneyManagerDataManipulation managerDataManipulation;
	
	@Autowired
	private MoneyManagerRepository moneyManagerRepository;
	
	private IPersonalActivity personalActivity;
	
	public static MoneyManagerFactory getInstance() {
		if(moneyManagerFactory==null) {
			moneyManagerFactory = new MoneyManagerFactory();
		}
		return moneyManagerFactory;
	}

	public static MoneyManagerFactory getMoneyManagerFactory() {
		return moneyManagerFactory;
	}

	public IMoneyManagerDataManipulation getManagerDataManipulation() {
		return managerDataManipulation;
	}

	public MoneyManagerRepository getMoneyManagerRepository() {
		return moneyManagerRepository;
	}

	public IPersonalActivity getPersonalActivity() {
		if(personalActivity==null) {
			personalActivity = new PersonalActivity();
		}
		return personalActivity;
	}
	
}
