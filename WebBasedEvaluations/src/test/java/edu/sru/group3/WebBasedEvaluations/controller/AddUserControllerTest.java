package edu.sru.group3.WebBasedEvaluations.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.controller.AddUserController;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

@Suite
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddUserControllerTest {
	

	private static User user = new User();
	BindingResult result;
	Model model;
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	CompanyRepository companyRepo;
	@Autowired
	LocationRepository locationRepo;
	@Autowired
	DepartmentRepository deptRepo;
	@Autowired
	private static Company co = new Company("testco");
	Authentication auth;
	MultipartFile file;
//	AddUserController controller = new AddUserController(userRepo,roleRepo,companyRepo,locationRepo,deptRepo);

	
	@Autowired
	AddUserController controller;
	

	@BeforeAll
	public static void  newUser() {
		
		user.setFirstName("Sam");
		user.setLastName("Thangiah");
		user.setCompanyName("Thangiah Inc");
		user.setDivisionBranch("Retroville");
		user.setRole(new Role("USER",co));
		user.setSupervisor(null);
		user.setEmail("sam.thangiah@sru.edu");
		user.setEncryptedPassword("test");
		
	}
	

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void addUserTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.addUser(null, null, null, null, null, null, null, null, null);
			attempt = true;
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		//We should not be able to add a null user if we try and it crashes it should also fail
		assertTrue(attempt && userRepo.count() == 0 );
		
		try {
			
			controller.addUser(user, result, model, auth, "Key", 1, "sort", 1, 1);
			attempt = true;
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		//We should be able to add a user and we know that we did so if the repo has an entry.
		assertTrue(attempt && userRepo.count() == 1);
		
	}
	public void SanityCheckTest() throws Exception {
		assertThat(controller).isNotNull();
	}


	
	@Test
	public void uploadUser2Test() {
		
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/uploaduser2", String.class)).isNotEmpty();
		boolean attempt = false;
		try {
			
			controller.uploaduser2(null, null, user, model, null, null, null, null, auth);
			attempt = true;
			
		}
		catch(Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && userRepo.count() == 0);
		
		try {
			
			controller.uploaduser2(file, 1, user, model, "Keyword", "Sort", 1, 1, auth);
			attempt = true;
		}
		
		catch(Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && userRepo.count() == 1);
	}
	
	
	
	

}
