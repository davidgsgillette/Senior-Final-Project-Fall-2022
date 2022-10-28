package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class MyUserDetailsTest{

	static User user;
	static MyUserDetails myUserDetails;
	
	@BeforeAll
	static void setup() {
		user = new User();
		user.setId((long) 7);
		user.setEmail("Test@Test.com");
		user.setPassword("Test");
		user.setRoleName("Test");
		myUserDetails = new MyUserDetails(user);
	}
	
	//Not sure how to test this yet b/c authorities is grabbed from elsewhere
	@Test
	void getAuthoritiesTest() {
		List<GrantedAuthority> actual = new ArrayList<>();
		assertEquals(1,2);
	}

	@Test
	void getPassword() {
		String actual = "Test";
		assertEquals(myUserDetails.getPassword(), actual);
	}

	@Test
	void getIDTest() {
		long actual = (long) 7;
		assertEquals(myUserDetails.getID(), actual);
	}

	@Test
	void getRolesTest() {
		String actual = "Test";
		assertEquals(myUserDetails.getUser().getRoleName(), actual);
	}

	@Test
	void getUsernameTest() {
		String actual = "Test@Test.com";
		assertEquals(myUserDetails.getUsername(), actual);
	}

	@Test
	void isAccountNonExpiredTest() {
		assertTrue(myUserDetails.isAccountNonExpired());
	}

	@Test
	void isAccountNonLockedTest() {
		assertTrue(myUserDetails.isAccountNonLocked());
	}

	@Test
	void isCredentialsNonExpiredTest() {
		assertTrue(myUserDetails.isCredentialsNonExpired());
	}

	@Test
	void isEnabledTest() {
		assertTrue(myUserDetails.isEnabled());
	}

}
