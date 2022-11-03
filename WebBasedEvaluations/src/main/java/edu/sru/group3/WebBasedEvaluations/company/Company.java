package edu.sru.group3.WebBasedEvaluations.company;


import java.util.ArrayList;
import java.util.Collection;
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
import edu.sru.group3.WebBasedEvaluations.domain.*;

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
	@ManyToMany(mappedBy = "companies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Privilege> privs;

	@NonNull
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Location> locations;
	
	
	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<EvalRole> evalRoles;
	
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Reviewee> reviewees;


	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Group> evalGroups;

	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Group> groups = new HashSet<Group>();
	
	
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<EvalTemplates> evalTemplates = new HashSet<EvalTemplates>();
	

	public Company() {

	}


	public Company(String companyName) {
		this.companyName = companyName;
		this.numEmployees = 0;
		this.numLocations = 0;
		this.locations = new HashSet<Location>();
		this.users = new HashSet<User>();
		this.roles = new HashSet<Role>();
		this.privs = new HashSet<Privilege>();
//		String roleName = companyName + " USER";
//		String roleName = "USER";
//		roles.add(new Role(roleName,this));
	}

	public boolean addPrivilege(Privilege priv) {
		this.privs.add(priv);
		return true;
	}

	public boolean addPrivss(Collection<Privilege> privs) {
		this.privs.addAll(privs);
		return true;
	}

	public boolean removePriv(Privilege priv) {

		if(this.privs.contains(priv)) {
			this.privs.remove(priv);			
			return true;
		}
		return false;
	}
	
	
	public boolean addReviewee(Reviewee reviewee) {
		this.reviewees.add(reviewee);
		return true;
	}

	public boolean addReviewees(Collection<Reviewee> reviewees) {
		this.reviewees.addAll(reviewees);
		return true;
	}

	public boolean removeReviewee(Reviewee reviewee) {

		if(this.reviewees.contains(reviewee)) {
			this.reviewees.remove(reviewee);			
			return true;
		}
		return false;
	}
	
	
	
	public boolean addGroup(Group group) {
		this.groups.add(group);
		return true;
	}

	public boolean addGroups(Collection<Group> groups) {
		this.groups.addAll(groups);
		return true;
	}

	public boolean removeGroup(Group group) {

		if(this.groups.contains(group)) {
			this.groups.remove(group);			
			return true;
		}
		return false;
	}
	
	
	
//	public Role getDefaultRole() {
//		String roleName = companyName + " USER";
//		return getRoleByName(roleName);
//	}
	public String getDefaultRoleName() {
		return companyName + " USER";
	}

	public Role getRoleByName(String roleName) {
		for(Role role : roles) {
			if(role.getName().toLowerCase().trim().equals(roleName.toLowerCase().trim())) {
				return role;
			}
		}
		return null;
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

	
	public boolean addEvalRole(EvalRole role) {		
		this.evalRoles.add(role);			
		return true;
	}

	public boolean removeEvalRole(EvalRole role) {

		if(this.evalRoles.contains(role)) {
			this.evalRoles.remove(role);			
			return true;
		}
		return false;
	}
	
	
	public Set<Privilege> getPrivs() {
		return privs;
	}


	public void setPrivs(Set<Privilege> privs) {
		this.privs = privs;
	}


	public Set<EvalRole> getEvalRoles() {
		return evalRoles;
	}


	public Set<Group> getEvalGroups() {
		return evalGroups;
	}


	public void setEvalGroups(Set<Group> evalGroups) {
		this.evalGroups = evalGroups;
	}


	public Set<Group> getGroups() {
		return groups;
	}


	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}


	public void setEvalRoles(Set<EvalRole> evalRoles) {
		this.evalRoles = evalRoles;
	}


	public void setLocations(Set<Location> locations) {
		this.locations = locations;
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


	public Set<Reviewee> getReviewees() {
		return reviewees;
	}


	public void setReviewees(Set<Reviewee> reviewees) {
		this.reviewees = reviewees;
	}


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

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(HashSet<Location> locations) {
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

