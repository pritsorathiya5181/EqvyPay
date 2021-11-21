package com.eqvypay.Service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AuthenticationServiceTest {

	@Test
	public void shouldCheckIfAuthenticationServiceExists() {
		assertNotNull(new AuthenticationService());	
	}
}
