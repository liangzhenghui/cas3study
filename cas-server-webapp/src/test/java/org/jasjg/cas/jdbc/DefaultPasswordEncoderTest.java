package org.jasjg.cas.jdbc;

import org.jasig.cas.authentication.handler.DefaultPasswordEncoder;
import org.junit.Test;

public class DefaultPasswordEncoderTest {
	@Test
	public void test(){
		DefaultPasswordEncoder encoder=new DefaultPasswordEncoder("SHA-1");
		String password=encoder.encode("root");
		System.out.println(password);
	}
}
