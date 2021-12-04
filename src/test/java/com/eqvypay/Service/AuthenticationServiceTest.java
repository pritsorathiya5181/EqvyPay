package com.eqvypay.Service;

import static org.junit.Assert.assertNotNull;

import com.eqvypay.Service.authentication.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationServiceTest {

	@Test
	public void shouldCheckIfAuthenticationServiceExists() {
		assertNotNull(new AuthenticationService());
	}
}
