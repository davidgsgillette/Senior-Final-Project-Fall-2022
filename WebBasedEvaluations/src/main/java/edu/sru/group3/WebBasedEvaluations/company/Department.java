package edu.sru.group3.WebBasedEvaluations.company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import edu.sru.group3.WebBasedEvaluations.domain.User;


@Entity
@Table(name = "department")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	//department name
	private String name;
	//locations
	@ManyToMany
	@JoinTable(
			name = "dept_locations", 
			joinColumns = @JoinColumn(name = "dept_id"), 
			inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> locations;
	//users
	@ManyToMany
	@JoinTable(
			name = "dept_users", 
			joinColumns = @JoinColumn(name = "dept_id"), 
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	
	public Department() {
		users = new ArrayList<User>();
		locations = new ArrayList<Location>();
		this.name = "";
	}
	
	public Department(User user, Location loc, String name) {
		users = new ArrayList<User>();
		users.add(user);
		locations = new ArrayList<Location>();
		locations.add(loc);
		this.name = name;
	}
	
	public Department(List<User> users, List<Location> locations, String name) {
		this.users = users;
		this.locations = locations;
		this.name = name;
	}
	
	
	public boolean addUser(User user) {
		this.users.add(user);
		return true;
	}
	
	
	/*
	 * adds a list of users to the location
	 */
	public boolean addUsers(List<User> users) {
		
		for(User user : users) {
			this.users.add(user);
		}	
		return true;
	}
	
	/*
	 * removes a user from a location
	 */
	public boolean removeUser(User user) {
		if(this.users.contains(user)) {
			this.users.remove(user);
			return true;
		}
		return false;
	}	
	
	
	public void addLocation(Location loc) {
		this.locations.add(loc);
	}
	
	public void addLocations(List<Location> locations) {
		for(Location loc : locations) {
			this.locations.add(loc);
		}
	}
	
	public boolean removeLocation(Location loc) {
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
}
