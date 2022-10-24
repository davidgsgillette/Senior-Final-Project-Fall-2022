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
public class CountryTest {
	
	static long id = 1;
	static String name = "USA";
	static int numProv = 50;
	static Continent parent;
	static Province child;
	
	static Country country;

	@BeforeAll
	static public void initialize() {
		
		country = new Country(name, numProv, parent, child);
		country.setId(id);
		country.setNumProvinces(1);
		
		
	}
	
	@Test
	public void addProvinceTest() {
		
		assertTrue(country.addProvince(child));
		
	}
	
	@Test
	public void addProvincesTest() {
		
		List<Province> Provincies = new ArrayList<Province>();
		Province otherChild = new Province();
		Provincies.add(otherChild);
		
		assertTrue(country.addProvinces(Provincies));
		
	}
	
	@Test
	public void removeProvinceTest() {
		
		assertTrue(country.removeProvince(child));
		
	}
	
	@Test
	public void getIdTest() {
		
		
		assertTrue(country.getId() == id);
	}
	
	@Test
	public void setIdTest() {
		
		long newId = 3;
		country.setId(newId);
		assertTrue(country.getId() == 3);
		
	}
	
	@Test
	public void getCountryNameTest() {
		
		assertTrue(country.getCountryName() == name);
		
	}
	
	@Test
	public void setCountryNameTest() {
		
		String newName = "Canada";
		country.setCountryName(newName);
		
		assertTrue(country.getCountryName() == newName);
		
	}
	
	@Test
	public void getNumProvincesTest() {
		
		assertTrue(country.getNumProvinces() == 1);
		
	}
	
	@Test
	public void setNumProvincesTest() {
		
		country.setNumProvinces(4);
		assertTrue(country.getNumProvinces() == 4);
		
	}
	
	@Test
	public void getParentContinentTest() {
		
		assertTrue(country.getParentContinent() == parent);
		
	}
	
	@Test
	public void setParentContinentTest() {
		
		Continent newContinent = new Continent();
		country.setParentContinent(newContinent);
		
		assertTrue(country.getParentContinent() == newContinent);
	}
	
	@Test
	public void getChildProvincesTest() {
		
		assertTrue(country.getChildProvinces() == child);
		
	}
	
	@Test
	public void setChildProvincesTest() {
		
		List<Province> provinces = new ArrayList<Province>();
		Province newProcience = new Province();
		provinces.add(newProcience);
		
		
		country.setChildProvinces(provinces);
		
		assertTrue(country.getChildProvinces() == newProcience);
		
	}
	
}
