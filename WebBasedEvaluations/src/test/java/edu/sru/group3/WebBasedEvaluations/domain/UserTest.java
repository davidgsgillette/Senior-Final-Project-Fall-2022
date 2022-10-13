package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
	static User user;

	@BeforeAll
	static void setup() {
		user = new User();
	}

	@Test
	void getPasswordTest() {
		String actual = "Test";
		user.setPassword(actual);
		assertEquals(user.getPassword(), actual);
	}

	@Test
	void getIdTest() {
		long actual = (long) 5;
		user.setId(actual);
		assertEquals(user.getId(), actual);
	}

	@Test
	void getNameTest() {
		String actual = "Bob";
		user.setName(actual);
		assertEquals(user.getName(), actual);
	}

	@Test
	void getEmailTest() {
		String actual = "Test@Test.com";
		user.setEmail(actual);
		assertEquals(user.getEmail(), actual);
	}

	@Test
	void getRolesTest() {
		String actual = "Test";
		user.setRoles(actual);
		assertEquals(user.getRoles(), actual);
	}

//	@Test
//	void getEvaluatorTest() {
//		List<Evaluator> actual = new ArrayList<>();
//		user.setEvaluator(actual);
//		assertEquals(user.getEvaluator(), actual);
//	}

	@Test
	void getFirstNameTest() {
		String actual = "Test";
		user.setFirstName(actual);
		assertEquals(user.getFirstName(), actual);
	}

	@Test
	void getLastNameTest() {
		String actual = "Test";
		user.setLastName(actual);
		assertEquals(user.getLastName(), actual);
	}

	@Test
	void getSuffixNameTest() {
		String actual = "Test";
		user.setSuffixName(actual);
		assertEquals(user.getSuffixName(), actual);
	}

	@Test
	void getResetTest() {
		user.setReset(true);
		assertTrue(user.getReset());
		user.setReset(false);
		assertFalse(user.getReset());
	}

	@Test
	void isResetPTest() {
		user.setResetP(true);
		assertTrue(user.isResetP());
		user.setResetP(false);
		assertFalse(user.isResetP());
	}

	@Test
	void getDateOfHireTest() {
		String actual = "Test";
		user.setDateOfHire(actual);
		assertEquals(user.getDateOfHire(), actual);
	}

	@Test
	void getJobTitleTest() {
		String actual = "Test";
		user.setJobTitle(actual);
		assertEquals(user.getJobTitle(), actual);
	}

	@Test
	void getSupervisorTest() {
		String actual = "Test";
		user.setSupervisor(actual);
		assertEquals(user.getSupervisor(), actual);
	}

	@Test
	void getCompanyNameTest() {
		String actual = "Test";
		user.setCompanyName(actual);
		assertEquals(user.getCompanyName(), actual);
	}

	@Test
	void getDivisionBranchTest() {
		String actual = "Test";
		user.setDivisionBranch(actual);
		assertEquals(user.getDivisionBranch(), actual);
	}

}