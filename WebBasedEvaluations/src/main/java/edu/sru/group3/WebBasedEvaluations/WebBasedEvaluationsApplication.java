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
		
		Company co = new Company("Thangiah Manufacturing LLC");
		Company co2 = new Company("Test Company 2");
		
		World world = new World();
//		worldRepo.save(world);
		
		Continent continent = new Continent("testContinent", world);
//		continentRepo.save(continent);
		
		world.addContinent(continent);
//		worldRepo.save(world);
	
		Country country = new Country("testCountry", continent);
//		countryRepo.save(country);
		
		continent.addCountry(country);
//		continentRepo.save(continent);
		

		Province province = new Province("testProvince", country);
//		provinceRepo.save(province);
		
		country.addProvince(province);
//		countryRepo.save(country);
		
		
		City city = new City("testCity", province);
//		cityRepo.save(city);
		
		province.addCity(city);
		
		
		LocationGroup locGroup = new LocationGroup();
		LocationGroup locGroup2 = new LocationGroup();
		
		Location loc = new Location("testLocation", city, co, locGroup);
		Location loc2 = new Location("testLocation", city, co2, locGroup2);
//		locationRepo.save(loc);
			
			
		Role adminRole = new Role("ADMIN",co);
		Role adminRole2 = new Role("ADMIN2",co2);
		Role testRole1 = new Role("TEST_ROLE_1",co);
		Role testRole2 = new Role("TEST_ROLE_2",co2);
//		
	
		
		co.addRole(adminRole);
		co.addRole(testRole1);
		co.addRole(testRole2);
		co2.addRole(adminRole2);
		co2.addRole(testRole1);
		co2.addRole(testRole2);
//		companyRepo.save(co);
//		adminRole.addCompany(co);
		
		
		
		
		User use1 = new User("jimmy neutron","fname","lname","admin@gmail.com","$2y$12$.ahxo5UdngIuZdKSu91Jn.VtHjjYCh04.lpM5LNFdICjEjechMDQ", 999991, "N/A", "N/A", null, "N/A","admin dept", co, adminRole,true,true);
		User use2 = new User("jimmy2 neutron2","fname2","lname2","admin2@gmail.com","$2y$12$.ahxo5UdngIuZdKSu91Jn.VtHjjYCh04.lpM5LNFdICjEjechMDQ", 999991, "N/A", "N/A", null, "N/A","admin dept", co2, adminRole2,true,false);
//		adminRole.addUser(use1);
//		roleRepo.save(adminRole);
		
		
		use1.setEncryptedPassword("test");
		use1.setReset(false);
		use2.setEncryptedPassword("test");
		use2.setReset(false);
//		userRepo.save(use1);
		
		
		
		
		
		Department dept = new Department(use1, loc, "testing dept", null,null,co);
		Department dept2 = new Department(use2, loc2, "testing dept2", null,null,co2);
		
		use1.addDepartment(dept);
		loc.addDept(dept);
		use2.addDepartment(dept2);
		loc2.addDept(dept2);
//		userRepo.save(use1);
//		locationRepo.save(loc);
		
		
		
		
		locGroup.addLocation(loc);
		locGroup2.addLocation(loc2);
//		locGroupRepo.save(locGroup);
		
		city.addLocation(loc);
		city.addLocation(loc2);
//		
	
		
		
		co.addLocation(loc);
		co2.addLocation(loc2);
//		companyRepo.save(co);
		
		
//		Role adminRole = new Role("Global Admin");
//		roleRepo.save(adminRole);
		
		Privilege priv = new Privilege("ADMIN",adminRole, locGroup, dept, co, true,true,true,false);

		Privilege priv2 = new Privilege("ADMIN2", adminRole2, locGroup2, dept2, co2, true,true,true,false);
//		privRepo.save(priv);
		
		adminRole.addPrivilege(priv);
		adminRole2.addPrivilege(priv2);
		priv.addRole(adminRole);
		priv2.addRole(adminRole2);
		
//		roleRepo.save(adminRole);
		
		co.addUser(use1);
		use1.addLocation(loc);
		
		co2.addUser(use2);
		use2.addLocation(loc2);
//		companyRepo.save(co);
//		userRepo.save(use1);
		
		locGroupRepo.save(locGroup);
		provinceRepo.save(province);
		deptRepo.save(dept);
		privRepo.save(priv);
		userRepo.save(use1);
		locationRepo.save(loc);
		roleRepo.save(testRole1);
		roleRepo.save(testRole2);
		
		
		locGroupRepo.save(locGroup2);
		deptRepo.save(dept2);
		privRepo.save(priv2);
		userRepo.save(use2);
		locationRepo.save(loc2);
		//companyRepo.save(co2);
		
		
		
		
		
		
		//adding example companies. 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		 
		
	}
}