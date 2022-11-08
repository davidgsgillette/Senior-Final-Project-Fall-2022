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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.sru.group3.WebBasedEvaluations.domain.EvalTemplates;
import edu.sru.group3.WebBasedEvaluations.domain.Privilege;
import edu.sru.group3.WebBasedEvaluations.domain.User;


@Entity
@Table(name = "department")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	//department name
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "deptHead_user_id")
	private User deptHead;
	
	//locations
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "dept_locations", 
			joinColumns = @JoinColumn(name = "dept_id"), 
			inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<Location> locations;
	
	@ManyToMany(mappedBy = "depts", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<EvalTemplates> evalTemplates;
	
	//users
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "dept_users", 
			joinColumns = @JoinColumn(name = "dept_id"), 
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;
	
	
	@ManyToMany(mappedBy = "depts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Privilege> privileges;
	
	
	public Department() {
		users = new ArrayList<User>();
		locations = new ArrayList<Location>();
		this.name = "";
	}
	
	public Department(Company co) {
		users = new ArrayList<User>();
		locations = new ArrayList<Location>();
		this.name = "";
		this.company = co;
	}
	
	public Department(User user, Location loc, String name, Privilege priv, User deptHead, Company company) {
		users = new ArrayList<User>();
		users.add(user);
		locations = new ArrayList<Location>();
		this.privileges = new ArrayList<Privilege>();
		this.privileges.add(priv);
		locations.add(loc);
		this.name = name;
		this.deptHead = deptHead;
		this.company = company;
	}
	
	public Department(List<User> users, List<Location> locations, String name, List<Privilege> privs, Company company) {
		this.users = users;
		this.locations = locations;
		this.name = name;
		this.privileges = privs;
		this.company = company;
	}
	
	
	
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<EvalTemplates> getEvalTemplates() {
		return evalTemplates;
	}

	public void setEvalTemplates(List<EvalTemplates> evalTemplates) {
		this.evalTemplates = evalTemplates;
	}

	public void addPrivilege(Privilege priv) {
		this.privileges.add(priv);
	}
	
	public boolean removePrivilege(Privilege priv) {
		if(this.privileges.contains(priv)) {
			this.privileges.remove(priv);
			return true;
		}
		return false;
	}
	
	public boolean addUser(User user) {
		this.users.add(user);
		return true;
	}
	
	
	/*
	 * adds a list of users to the location
	 */
	public boolean addUsers(List<User> users) {
		
		this.users.addAll(users);
			
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
	
	
	public boolean addTemplate(EvalTemplates evalTemplate) {
		this.evalTemplates.add(evalTemplate);
		return true;
	}
	
	
	/*
	 * adds a list of users to the location
	 */
	public boolean addTemplate(List<EvalTemplates> evalTemplates) {
		
		this.evalTemplates.addAll(evalTemplates);
			
		return true;
	}
	
	/*
	 * removes a user from a location
	 */
	public boolean removeTemplate(EvalTemplates evalTemplate) {
		if(this.evalTemplates.contains(evalTemplate)) {
			this.evalTemplates.remove(evalTemplate);
			return true;
		}
		return false;
	}
	
	public void addLocation(Location loc) {
		this.locations.add(loc);
	}
	
	public void addLocations(List<Location> locations) {
		
		this.locations.addAll(locations);
		
	}
	
	public boolean removeLocation(Location loc) {
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			return true;
		}
		return false;
	}
	
	
	
	
	public User getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(User deptHead) {
		this.deptHead = deptHead;
	}

	public String getName() {
		return name;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
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
