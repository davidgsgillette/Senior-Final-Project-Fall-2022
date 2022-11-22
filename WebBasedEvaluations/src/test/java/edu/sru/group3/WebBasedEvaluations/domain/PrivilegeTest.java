package edu.sru.group3.WebBasedEvaluations.domain;

import lombok.NonNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;

@Suite
@SuiteDisplayName("Privilege Methods")
@IncludeClassNamePatterns(".*Tests")
public class PrivilegeTest {
	
	static Role role;
	static Privilege p;
	static Company testCo;
	static User user;
	static Department dep;
	static Location loc;
	static LocationGroup locG;
	@BeforeAll
	static void setup() {
		testCo = new Company("test");
		user = new User();
		user.setName("test");
		dep = new Department();
		dep.addUser(user);
		loc = new Location();
		
		locG = new LocationGroup(loc, p, "test",testCo);
		p = new Privilege("testAll", role, true, true, true, true);
		p.addCompany(testCo);
		p.addDept(dep);
		p.addLocGroup(locG);
		role = new Role("test", user, p, testCo);
		p.addRole(role);
		user.setRole(role);
		
	}

	@Test
    public void addRemoveCompanyTest() {
	}
    
	@Test
    public void addRemoveLocGroupTest() {
	}
    
	@Test
    public void addRemoveDeptTest() {
	}

	@Test
    public void addRemoveRoleTest() {
    }
    
    //getters and setters.
    
	@Test
    public void getReadTest() {
	}
    
	@Test
	public void getSetEditEvaluatorTest() {
	}

	@Test
	public void getSetCompaniesTest() {
	}

	@Test
	public void isSetGetRTest() {
	}

	@Test
	public void isSetGetWTest() {
	}

	@Test
	public void isSetGetDTest() {
	}

	@Test
	public void getEditEvaluateTest() {
	}

	@Test
	public void setEvaluateTest() {
	}
	
	@Test
	public void getSetDeptsTest() {
	}
	
	@Test
	public void getSetLocationGroupsTest() {
	}
	
	@Test
	public void getSetIdTest() {
	}

	@Test
	public void getSetNameTest() {
	}

	@Test
	public void getSetRolesTest() {
	}
    
}
