package com.eqvypay;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EqvyPayApplicationTest {

	@Test
	public void shouldInstantiateEqvyPayApplication(){
		assertNotNull(new EqvyPayApplication());
	}
}
