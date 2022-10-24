package edu.sru.group3.WebBasedEvaluations.company;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WorldTest {
	
	static World world; 
	static int numContinents = 4;
	static long id = 1;
	static Continent con;
	static List<Continent> childContinents;
	
	@BeforeAll
	static public void initialize() {
		
		con = new Continent();
		
		childContinents = new ArrayList<Continent>();
		childContinents.add(con);
		
		world = new World(numContinents, childContinents);
		
		world.setId(id);
		world.setNumContinents(numContinents);
		
	}
	
	@Test
	public void addContinentTest() {
		
		Continent newCon = new Continent();
		
		assertTrue(world.addContinent(newCon));
		
	}
	
	@Test
	public void addContinentsTest() {
		
		assertTrue(world.addContinent(childContinents));
		
	}
	
	@Test
	public void removeContinentTest() {
		
		assertTrue(world.removeContinent(con));
		
	}
	
	@Test
	public void getIdTest() {
		
		assertTrue(world.getId() == id);
		
	}
	
	@Test
	public void setIdTest() {
		
		long newId = 2;
		
		world.setId(newId);
		
		assertTrue(world.getId() == newId);
		
	}
	
	@Test
	public void getNumContinentsTest() {
		
		assertTrue(world.getNumContinents() == numContinents);
		
	}
	
	@Test
	public void setNumContinentsTest() {
		
		int newCon = 4;
		
		world.setNumContinents(newCon);
		
		assertTrue(world.getNumContinents() == newCon);
		
	}
	
	@Test
	public void getChildContinentsTest() {
		
		assertTrue(world.getChildContinents() == childContinents);
		
	}
	
	@Test
	public void setChildContinentsTest() {
		
		childContinents.clear();
		
		world.setChildContinents(childContinents);
		
		assertTrue(world.getChildContinents() == childContinents);
		
	}
	

}
