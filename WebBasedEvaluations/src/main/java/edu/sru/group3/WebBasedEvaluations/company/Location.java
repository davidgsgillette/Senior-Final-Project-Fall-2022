package edu.sru.group3.WebBasedEvaluations.company;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.lang.NonNull;

import edu.sru.group3.WebBasedEvaluations.domain.User;

/**Class for methods of a location object, almost exclusively made out of getters and setters.
 * also includes mapping for sql tables. 
 * @author David Gillette
 *
 */
@Entity
@Table(name = "location")
public class Location {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@NonNull
	private String locationName;
	
	
	
	


	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id", nullable = false)
	private City parentCity;
	
	
	@NonNull
	private int numEmployees;
	
	
	@NonNull
	@ManyToMany(mappedBy = "locations", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<User> users;

	
	public Location() {
		
	}
	
	public Location(City parentCity, int numEmployees, List<User> users, String locationName) {
		this.numEmployees = numEmployees;
		this.parentCity = parentCity;
		this.locationName = locationName;
//		this.users = users;
	}
	
	public Location(City parentCity, int numEmployees, User user, String locationName) {
		this.numEmployees = numEmployees;
		this.parentCity = parentCity;
		this.locationName = locationName;
//		this.users = new ArrayList<>();
//		users.add(user);
	}
		
	/*
	 * adds a single user to a location. 
	 */
//	public boolean addUser(User user) {
//		this.users.add(user);
//		return true;
//	}
	
	
	/*
	 * adds a list of users to the location
	 */
//	public boolean addUsers(List<User> users) {
//		
//		for(User user : users) {
//			this.users.add(user);
//		}	
//		return true;
//	}
	
	/*
	 * removes a user from a location
	 */
//	public boolean removeUser(User user) {
//		if(this.users.contains(user)) {
//			this.users.remove(user);
//			return true;
//		}
//		return false;
//	}	
	
	
	
	//standard getters and setters 
	
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public City getParentCity() {
		return parentCity;
	}

	public void setParentCity(City parentCity) {
		this.parentCity = parentCity;
	}
	
	public int getNumEmployees() {
		return numEmployees;
	}


	public void setNumEmployees(int numEmployees) {
		this.numEmployees = numEmployees;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public City getHomeCity() {
		return parentCity;
	}


	public void setHomeCity(City homeCity) {
		this.parentCity = homeCity;
	}


//	public List<User> getUsers() {
//		return users;
//	}
//
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
	
	
	
}
