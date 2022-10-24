package edu.sru.group3.WebBasedEvaluations.company;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.Role;
import edu.sru.group3.WebBasedEvaluations.domain.User;

/*
 * Class for methods of the company object. hold info such as number of employees and head of tree of locations. 
 * @author David Gillette
 */
@Entity
@Table(name = "company")
public class Company {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	private String companyName;
	
	@NonNull
	private int numEmployees;
	
	@NonNull
	private int numLocations;
	
	//maps company id to user. 
	@NonNull
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users;
	//private List<User> users = new ArrayList<>();
	
	
	@NonNull
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Location> locations;
	
	
	@ManyToMany
    @JoinTable(
            name = "company_roles", 
            joinColumns = @JoinColumn(name = "company_id"), 
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
	
	
	public Company() {
		
	}
	
	
	public Company(String companyName) {
		this.companyName = companyName;
		this.numEmployees = 0;
		this.numLocations = 0;
		this.locations = new ArrayList<Location>();
		this.users = new HashSet<User>();
		this.roles = new HashSet<Role>();
	}
	
	
	public boolean addRole(Role role) {
		this.roles.add(role);
		return true;
	}
	
	public boolean addRoles(List<Role> roles) {
		for(Role role : roles) {
			this.roles.add(role);
		}		
		return true;
	}
	
	public boolean removeRole(Role role) {
		
		if(this.roles.contains(role)) {
			this.roles.remove(role);			
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	public boolean addLocation(Location loc) {
		this.locations.add(loc);
		this.numLocations++;
		return true;
	}
	
	public boolean addLocations(List<Location> locs) {
		for(Location loc : locs) {
			this.locations.add(loc);
			this.numLocations++;
		}		
		return true;
	}
	
	public boolean removeLocation(Location loc) {
		
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			this.numLocations--;
			return true;
		}
		return false;
	}
	
	public boolean addUser(User user) {
		this.users.add(user);
		this.numEmployees++;
		return true;
	}
	
	public boolean addUsers(List<User> userList) {
		for(User user : userList) {
			this.users.add(user);
			this.numEmployees++;
		}		
		return true;
	}
	
	public boolean removeUser(User user) {
		
		if(this.users.contains(user)) {
			this.users.remove(user);
			this.numEmployees--;
			return true;
		}
		return false;
	}
	
	
	//getters and setters
	

	public Long getId() {
		return id;
	}

	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getNumEmployees() {
		return numEmployees;
	}

	public void setNumEmployees(int numEmployees) {
		this.numEmployees = numEmployees;
	}

	public int getNumLocations() {
		return numLocations;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public void setNumLocations(int numLocations) {
		this.numLocations = numLocations;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
}
	
