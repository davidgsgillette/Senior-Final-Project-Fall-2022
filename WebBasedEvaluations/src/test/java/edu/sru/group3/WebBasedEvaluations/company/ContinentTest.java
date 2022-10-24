package edu.sru.group3.WebBasedEvaluations.company;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.group3.WebBasedEvaluations.domain.User;

@Suite
@SpringBootTest
public class ContinentTest {
	
	static String name = "North America";
	static int numCountires = 3;
	static World parent;
	static Country child;
	
	static Continent continent;
	
	@BeforeAll
	static public void initialize() {
		
		continent = new Continent(name, numCountires, parent, child);
		continent.setNumCountries(3);
		
	}
	
	@Test
	public void addCountryTest() {
		
		Country country = new Country();
		
		assertTrue(continent.addCountry(country));
		
	}
	
	@Test
	public void addCountryListTest() {
		
		List<Country> countries = new ArrayList<Country>();
		Country otherChild = new Country();
		countries.add(otherChild);
		
		assertTrue(continent.addCountry(countries));
		
	}
	
	@Test
	public void removeCountryTest() {
		
		assertTrue(continent.removeCountry(child));
		
	}
	
	@Test
	public void getIdTest() {
		
		long id = 1;
		continent.setId(id);
		assertThat(continent.getId() == id); 
		
	}
	
	@Test
	public void setIdTest() {
		
		long id = 1;
		continent.setId(id);
		assertThat(continent.getId() == id);
		
	}
	
	@Test
	public void getContinentNameTest() {
		
		assertThat(continent.getContinentName().contains(name));
		
	}
	
	@Test
	public void setContinentNameTest() {
		
		continent.setContinentName("South America");
		assertThat(continent.getContinentName().contains("South America"));
		
	}
	
	@Test
	public void getNumCountriesTest() {
		
		assertTrue(continent.getNumCountries() == 3);
		
	}
	
	@Test
	public void setNumCountriesTest() {
		
		continent.setNumCountries(8);
		assertTrue(continent.getNumCountries() == 8);
		
	}
	
	@Test
	public void getParentWorldTest() {
		
		assertTrue(continent.getParentWorld() == parent);
		
	}
	
	@Test
	public void setParentWorldTest() {
		
		World planet = new World();
		continent.setParentWorld(planet);
		assertTrue(continent.getParentWorld() == planet);
	}
	
	@Test
	public void getChildCountriesTest() {
		
		assertTrue(continent.getChildCountries() == child);
		
	}
	
	@Test
	public void setChildCountriesTest() {
		
		List<Country> countries = new ArrayList<Country>();
		Country otherChild = new Country();
		countries.add(otherChild);
		
		continent.setChildCountries(countries);
		
		assertThat(continent.getChildCountries() == countries);
		
		
	}

}
