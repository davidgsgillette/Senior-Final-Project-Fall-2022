package edu.sru.group3.WebBasedEvaluations.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;



@Suite
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddUserControllerTest {
	
	
	@Autowired
	AddUserController controller;
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void SanityCheckTest() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void addUserTest() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/adduser/", String.class)).isNotEmpty();
	}
	
	@Test
	public void uploadUser2Test() {
		
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/uploaduser2", String.class)).isNotEmpty();
		
	}
	
	
	
	

}
