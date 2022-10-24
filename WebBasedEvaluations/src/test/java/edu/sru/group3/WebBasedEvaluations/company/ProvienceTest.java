package edu.sru.group3.WebBasedEvaluations.company;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SpringBootTest
public class ProvienceTest {
	
	static Province pro;
	static String name = "test Name";
	static int numCities = 6;
	static long id = 1;
	static Country parentCountry;
	static City city;
	static List<City> cities;
	
	@BeforeAll
	static public void initialize() {
		
		parentCountry = new Country();
		
		city = new City(); 
		
		cities = new ArrayList<City>();
		cities.add(city);
		
		
		pro = new Province(name, numCities, parentCountry, cities);
		
		pro.setId(id);
		
	}
	
	@Test
	public void addCity() {
		
		City city = new City();
		
		assertTrue(pro.addCity(city));
		
	}
	
	@Test
	public void addCities() {
		
		assertTrue(pro.addCities(cities));
		
	}
	
	@Test
	public void removeCity() {
		
		assertTrue(pro.removeCity(city));
		
	}
	
	@Test
	public void getId() {
		
		assertTrue(pro.getId() == id);
		
	}
	
	@Test
	public void setId() {
		
		long otherId = 2;
		
		pro.setId(otherId);
		
		assertTrue(pro.getId() == otherId);
		
	}
	
	@Test
	public void getProvinceName() {
		
		assertTrue(pro.getProvinceName() == name);
		
	}
	
	@Test
	public void setProvinceName() {
		
		String newName = "Name 2 electric boogallo";
		
		pro.setProvinceName(newName);
		
		assertTrue(pro.getProvinceName() == newName);
		
	}
	
	@Test
	public void getNumCities() {
		
		assertTrue(pro.getNumCities() == numCities);
		
	}
	
	@Test
	public void setNumCities() {
		
		int newCities = 3;
		
		pro.setNumCities(newCities);
		
		assertTrue(pro.getNumCities() == newCities);
		
	}
	
	@Test
	public void getParentCountry() {
		
		assertTrue(pro.getParentCountry() == parentCountry);
		
	}
	
	@Test
	public void setParentCountry() {
		
		Country country = new Country();
		
		pro.setParentCountry(country);
		
		assertTrue(pro.getParentCountry() == country);
		
	}
	
	@Test
	public void getChildCities() {
		
		assertTrue(pro.getChildCities() == cities);
		
	}
	
	@Test
	public void setChildCities() {
		
		cities.clear();
		
		pro.setChildCities(cities);
		
		assertTrue(pro.getChildCities() == cities);
		
	}

}
