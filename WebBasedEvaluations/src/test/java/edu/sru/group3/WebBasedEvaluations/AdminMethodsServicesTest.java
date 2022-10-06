package edu.sru.group3.WebBasedEvaluations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import antlr.collections.List;

import org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;
import edu.sru.group3.WebBasedEvaluations.controller.UserController;
import edu.sru.group3.WebBasedEvaluations.controller.AddUserControllerTest;

//https://junit.org/junit5/docs/current/user-guide/#overview
@Suite
@SuiteDisplayName("Admin Methods")
@IncludeClassNamePatterns(".*Tests")
public class AdminMethodsServicesTest {
	private static User user = new User();
	private static User user2 = new User();
	private static User user3 = new User();
	@Autowired
	UserRepository userRepo;
	
	AdminMethodsService adminMeth = new AdminMethodsService(userRepo);

	@BeforeAll
	public static void newUser() {
		
		// User missing some details (Job Title & Date of Hire)
		user.setFirstName("Sam");
		user.setLastName("Thangiah");
		user.setCompanyName("Thangiah Inc");
		user.setDivisionBranch("Retroville");
		user.setRoles("USER");
		user.setSupervisor("Jimmy");
		user.setEmail("sam.thangiah@sru.edu");
		user.setEncryptedPassword("test");
		
		// User with all valid information
		user2.setFirstName("Dalton");
		user2.setLastName("Stenzel");
		user2.setEmail("daltonrstenzel@gmail.com");
		user2.setRoles("USER");
		user2.setEncryptedPassword("test");
		
		user2.setCompanyName("Thangiah Inc");
		user2.setDivisionBranch("Retroville");
		user2.setSupervisor("Jimmy");
		user2.setDateOfHire("10/15/2022");
		user2.setJobTitle("Assistant");
		
		// User with all information, but has errors (email has space)
		user3.setFirstName("Dalton");
		user3.setLastName("Stenzel");
		user3.setEmail("daltonrstenzel @gmail.com");
		user3.setRoles("USER");
		user3.setEncryptedPassword("test");
		
		user3.setCompanyName("Thangiah Inc");
		user3.setDivisionBranch("Retroville");
		user3.setSupervisor("Jimmy");
		user3.setDateOfHire("10/15/2022");
		user3.setJobTitle("Assistant");


	}
	
    @Test
    public void firstNameTest() {
        String name = "Sam";
        user.setLastName("Neutron");
        assertEquals(user.getFirstName(), name);

    }
    
    @Test
    public void adminMethoCapTest() {
    	String capVal = adminMeth.capitalize("test");
        String finalVal = "Test";
        assertEquals(capVal, finalVal);

    }
    
    @Test
    public void adminMethoSpaceTest() {
    	//String capVal = adminMeth.capitialize("test");
        //String finalVal = "Test";
        assertTrue(adminMeth.hasSpace(user3.getEmail()));
        assertFalse(adminMeth.hasSpace(user2.getEmail()));

    }
    
    
    @Test
    public void adminMethoCheckTest() {
        assertFalse(adminMeth.checkAndUpdate(user));
        assertTrue(adminMeth.checkAndUpdate(user2));
        assertFalse(adminMeth.checkAndUpdate(user3));

    }



}

