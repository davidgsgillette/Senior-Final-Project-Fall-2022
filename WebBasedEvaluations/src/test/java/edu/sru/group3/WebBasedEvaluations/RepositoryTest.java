package edu.sru.group3.WebBasedEvaluations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.controller.AddUserController;
import edu.sru.group3.WebBasedEvaluations.domain.MyUserDetails;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.service.AdminMethodsService;
import edu.sru.group3.WebBasedEvaluations.service.UserService;

@ExtendWith(SpringExtension.class) //Replaces @RunWith(SpringRunner.class) in JUnit 5

@WebMvcTest(AddUserController.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RepositoryTest {

	@Autowired
	private static User user;
	@Autowired
	private static Company co = new Company("testco");
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	CompanyRepository companyRepo;
	@Autowired
	LocationRepository locationRepo;
	@Autowired
	DepartmentRepository deptRepo;
	//@Autowired
	@MockBean
	AdminMethodsService adminMethodsService;

	//@Autowired
	//AdminMethodsService adminMeth = new AdminMethodsService(userRepo);

	Integer perPage = 0;
	Integer currPage = 1;
	String keyword = "";
	String sort = "id";
	Integer sortOr = 1;
	BindingResult result;
	Model model;
	Authentication auth;

	@Test
	public void saveRepoTest() {
		MyUserDetails userD = new MyUserDetails(userRepo.findByEmail("jimmy@gmail.com"));
		
		//auth.setAuthenticated(true);
		
		assertEquals(userD.getUsername(), "jimmy@gmail.com");


		AddUserController addCon = new AddUserController(userRepo,roleRepo,companyRepo,locationRepo,deptRepo);
		User user2 = new User();
		// user2.setId((long)1);

		user2.setFirstName("test2");
		user2.setLastName("Stenzel");
		user2.setEmail("test2@gmail.com");
		user2.setRole(new Role("USER",co));
		user2.setEncryptedPassword("test");

		user2.setCompanyName("Thangiah Inc");
		user2.setDivisionBranch("Retroville");
		user2.setSupervisor("Jimmy");
		user2.setDateOfHire("10/15/2022");
		user2.setJobTitle("Assistant");
		addCon.addUser(user2, result, model, auth, keyword, perPage, sort, currPage, sortOr);

		//userRepo.save(user2);
		User user3 = userRepo.findByEmail("test2@gmail.com");
		// System.out.println(size);
		assertEquals(user3.getFirstName(), "test2");
		//assertEquals(userRepo.count(), 87);

	}

}
