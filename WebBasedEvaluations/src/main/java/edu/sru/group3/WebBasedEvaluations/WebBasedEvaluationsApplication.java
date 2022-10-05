package edu.sru.group3.WebBasedEvaluations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import edu.sru.group3.WebBasedEvaluations.company.City;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Continent;
import edu.sru.group3.WebBasedEvaluations.company.Country;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;
import edu.sru.group3.WebBasedEvaluations.company.Province;
import edu.sru.group3.WebBasedEvaluations.company.World;
import edu.sru.group3.WebBasedEvaluations.domain.Evaluator;
import edu.sru.group3.WebBasedEvaluations.domain.Group;
import edu.sru.group3.WebBasedEvaluations.domain.Reviewee;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.CityRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ContinentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.CountryRepository;
import edu.sru.group3.WebBasedEvaluations.repository.DepartmentRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationGroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.LocationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.ProvinceRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;
import edu.sru.group3.WebBasedEvaluations.repository.WorldRepository;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebBasedEvaluationsApplication {
//adding a comment to test merge. 
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WebBasedEvaluationsApplication.class, args);
		
		
		//comment these out if you are not creating the DB fresh, They just add one company for the admin user. 
		InitUsers startingUsers = new InitUsers();
		startingUsers.createBaseUsers(configurableApplicationContext);

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
		
		//making instances to add to the tables. 
		
		Company co = new Company("testCO");
		companyRepo.save(co);
		
		
		User use1 = new User("jimmy neutron","fname","lname","admin@gmail.com","$2y$12$.ahxo5UdngIuZdKSu91Jn.VtHjjYCh04.lpM5LNFdICjEjechMDQ","ADMIN", 999991, "N/A", "N/A", "N/A", "N/A", co);
		use1.setEncryptedPassword("test");
		userRepo.save(use1);
		
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
		
		
		
		co.addUser(use1);
		//loc.addUser(use1);
		use1.addLocation(loc);
		companyRepo.save(co);
		//locationRepo.save(loc);	
		//userRepo.save(use1);

		
	}
}