package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PasswordResetTokenTest {
	static PasswordResetToken passResTok;
	@BeforeAll
	static void setup() {
		passResTok = new PasswordResetToken();
	}
	
	@Test
	public void getTokenTest() {
		String actual = "Test";
		passResTok.setToken(actual);
		assertEquals(passResTok.getToken(), actual);
	}

	@Test
	public void getUserTest() {
		User actual = new User();
		passResTok.setUser(actual);
		assertEquals(passResTok.getUser(), actual);
	}

	@Test
	public void getUserIdResetTest() {
		long actual = (long) 4;
		passResTok.setUserIdReset(actual);
		assertEquals(passResTok.getUserIdReset(), actual);
	}
	
	@Test
	public void getExpireTimeTest() {
		LocalDate actualDate = LocalDate.now();
		LocalTime actualTime = LocalTime.now();
		passResTok.setExpireTime(actualDate, actualTime);
		assertEquals(passResTok.getExpireTime(), actualTime);
	}
	
	@Test
	public void getExpiredDateTest() {
		LocalDate actualDate = LocalDate.now();
		LocalTime actualTime = LocalTime.now();
		passResTok.setExpireTime(actualDate, actualTime);
		assertEquals(passResTok.getExpiredDate(), actualDate);
	}

}