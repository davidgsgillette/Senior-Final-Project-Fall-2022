package edu.sru.group3.WebBasedEvaluations;


import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import edu.sru.group3.WebBasedEvaluations.company.City;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Continent;
import edu.sru.group3.WebBasedEvaluations.company.Country;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;
import edu.sru.group3.WebBasedEvaluations.company.Province;
import edu.sru.group3.WebBasedEvaluations.company.World;
import edu.sru.group3.WebBasedEvaluations.domain.Privilege;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.CityRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ContinentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CountryRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationGroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.PrivilegeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ProvinceRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.repository.WorldRepository;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebBasedEvaluationsApplication {
//adding a comment to test merge. 
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		
		System.out.println("STARTING WEB APP\n");
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WebBasedEvaluationsApplication.class, args);
		System.out.println("\nFINISHED STARTING WEB APP app\n");
		System.out.println("STARTING LOADING OF TEST ADMIN USER\n");
		
		//comment these out if you are not creating the DB fresh, They just add one company for the admin user. 
		InitUsers startingUsers = new InitUsers();
		startingUsers.createBaseUsers(configurableApplicationContext);
		System.out.println("\nFINISHED LOADING TEST ADMIN USER");
	}
	
}


class InitUsers{
	
	
	
	
	public InitUsers() {
		
	}
	public void createBaseUsers(ConfigurableApplicationContext configurableApplicationContext) {
		
		//create company structure
		
		//repos
		WorldRepository worldRepo = configurableApplicationContext.getBean(WorldRepository.class);
		ContinentRepository continentRepo = configurableApplicationContext.getBean(ContinentRepository.class);
		CountryRepository countryRepo = configurableApplicationContext.getBean(CountryRepository.class);
		ProvinceRepository provinceRepo = configurableApplicationContext.getBean(ProvinceRepository.class);
		CityRepository cityRepo = configurableApplicationContext.getBean(CityRepository.class);		
		LocationRepository locationRepo=configurableApplicationContext.getBean(LocationRepository.class);
		
		UserRepository userRepo=configurableApplicationContext.getBean(UserRepository.class);
		CompanyRepository companyRepo=configurableApplicationContext.getBean(CompanyRepository.class);
		LocationGroupRepository locGroupRepo = configurableApplicationContext.getBean(LocationGroupRepository.class);
		DepartmentRepository deptRepo =  configurableApplicationContext.getBean(DepartmentRepository.class);
		RoleRepository roleRepo =  configurableApplicationContext.getBean(RoleRepository.class);
		PrivilegeRepository privRepo = configurableApplicationContext.getBean(PrivilegeRepository.class);
		//making instances to add to the tables. 
		
		Company co = new Company("testCO");
		companyRepo.save(co);
		
		
		
		World world = new World();
		worldRepo.save(world);
		
		Continent continent = new Continent("testContinent", world);
		continentRepo.save(continent);
		
		world.addContinent(continent);
		worldRepo.save(world);
	
		Country country = new Country("testCountry", continent);
		countryRepo.save(country);
		
		continent.addCountry(country);
		continentRepo.save(continent);
		
		
		
		
		
		Province province = new Province("testProvince", country);
		provinceRepo.save(province);
		
		country.addProvince(province);
		countryRepo.save(country);
		
		
		City city = new City("testCity", province);
		cityRepo.save(city);
		
		province.addCity(city);
		provinceRepo.save(province);
		
		LocationGroup locGroup = new LocationGroup();
		locGroupRepo.save(locGroup);
		
		Location loc = new Location("testLocation", city, co, locGroup);
		locationRepo.save(loc);
			
			
		Role adminRole = new Role("ADMIN");
		Role testRole1 = new Role("TEST_ROLE_1");
		Role testRole2 = new Role("TEST_ROLE_2");
		
		
		
		
		
//		
	
		
		co.addRole(adminRole);
		co.addRole(testRole1);
		co.addRole(testRole2);
//		adminRole.addCompany(co);
//		testRole1.addCompany(co);
//		testRole2.addCompany(co);
		
		
		roleRepo.save(testRole1);
		roleRepo.save(testRole2);
		
		User use1 = new User("jimmy neutron","fname","lname","admin@gmail.com","$2y$12$.ahxo5UdngIuZdKSu91Jn.VtHjjYCh04.lpM5LNFdICjEjechMDQ", 999991, "N/A", "N/A", "N/A", "N/A", co, adminRole);
//		adminRole.addUser(use1);
//		roleRepo.save(adminRole);
		userRepo.save(use1);
		
		use1.setEncryptedPassword("test");
		use1.setReset(false);
		userRepo.save(use1);
		
		
		
		
		
		Department dept = new Department(use1, loc, "testing dept", null);
		deptRepo.save(dept);
		
		use1.addDepartment(dept);
		loc.addDept(dept);
		userRepo.save(use1);
		locationRepo.save(loc);
		
		
		
		
		locGroup.addLocation(loc);
		locGroupRepo.save(locGroup);
		
		city.addLocation(loc);
		cityRepo.save(city);
	
		
		
		co.addLocation(loc);
		companyRepo.save(co);
		
		
//		Role adminRole = new Role("Global Admin");
//		roleRepo.save(adminRole);
		
		Privilege priv = new Privilege("ADMIN",adminRole, locGroup,dept, true,true,true,false);
		privRepo.save(priv);
		
		adminRole.addPrivilege(priv);
		priv.addRole(adminRole);
		privRepo.save(priv);
		roleRepo.save(adminRole);
		
		co.addUser(use1);
		use1.addLocation(loc);
//		companyRepo.save(co);
		userRepo.save(use1);

		
	}
}