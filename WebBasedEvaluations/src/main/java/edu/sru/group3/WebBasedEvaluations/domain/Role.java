package edu.sru.group3.WebBasedEvaluations.domain;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.Location;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;


/*
 * This class is adapted from https://www.baeldung.com/role-and-privilege-for-spring-security-registration.
 * This class includes a role and holds the users that have that role and the privileges associated with this role. 
 * @Author David Gillette
 */
@Entity
@Table(name = "role")
public class Role {

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@NonNull
	@Column(unique=true)
	private String name;



	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<User> users;	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "roles_privileges", 
			joinColumns = @JoinColumn(name = "role_id"), 
			inverseJoinColumns = @JoinColumn(name = "privilege_id"))
	private Set<Privilege> privileges;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<Company> companies;

	public Role() {

	}

	public Role(String name) {
		this.name = name;
		this.users = new HashSet<User>();
		this.privileges = new HashSet<Privilege>();
		this.companies = new HashSet<Company>();
	}

	public Role(String name, Company co) {
		this.name = name;
		this.users = new HashSet<User>();
		this.privileges = new HashSet<Privilege>();
		this.companies = new HashSet<Company>();
		companies.add(co);

	}



	public Role(String name,  Privilege priv) {
		this.name = name;
		this.privileges = new HashSet<Privilege>();
		this.privileges.add(priv);
		this.users = new HashSet<User>();
		this.companies = new HashSet<Company>();

	}

	public Role(String name, User user, Privilege priv) {
		this.name = name;
		this.privileges = new HashSet<Privilege>();
		this.privileges.add(priv);
		this.users = new HashSet<User>();
		this.users.add(user);
		this.companies = new HashSet<Company>();
	}

	public Role(String name, Set<User> users, Set<Privilege> priv) {
		this.name = name;
		this.privileges = priv;
		this.users = users;
		this.companies = new HashSet<Company>();
	} 


	public Set<Company> readableCompanies(){
		HashSet<Company> readableCos = new HashSet<Company>();

		for(Privilege priv : this.privileges) {
			if(priv.getRead()) {
				readableCos.addAll(priv.getCompanies());
			}
		}
		return readableCos;
	}
	
	public Set<Company> writableCompanies(){
		HashSet<Company> writeableCos = new HashSet<Company>();

		for(Privilege priv : this.privileges) {
			if(priv.getWrite()) {
				writeableCos.addAll(priv.getCompanies());
			}
		}
		return writeableCos;
	}

	public Set<Company> deletableCompanies(){
		HashSet<Company> deletableCos = new HashSet<Company>();

		for(Privilege priv : this.privileges) {
			if(priv.getDelete()) {
				deletableCos.addAll(priv.getCompanies());
			}
		}
		return deletableCos;
	}
	
	public Set<Company> evalableCompanies(){
		HashSet<Company> evalableCos = new HashSet<Company>();

		for(Privilege priv : this.privileges) {
			if(priv.getEvaluate()) {
				evalableCos.addAll(priv.getCompanies());
			}
		}
		return evalableCos;
	}
	
	public Set<User> readableUsers(){
		HashSet<User> readableUsers = new HashSet<User>();

		for(Privilege priv : this.privileges) {
			if(priv.getRead()) {
				for(Department dept : priv.getDepts()) {
					readableUsers.addAll(dept.getUsers());
				}
			}
		}
		return readableUsers;
	}

	public Set<User> writableUsers(){
		HashSet<User> writableUsers = new HashSet<User>();

		for(Privilege priv : this.privileges) {
			if(priv.getWrite()) {
				for(Department dept : priv.getDepts()) {
					writableUsers.addAll(dept.getUsers());
				}
			}
		}
		return writableUsers;
	}

	public Set<User> deletableUsers(){
		HashSet<User> deletableUsers = new HashSet<User>();

		for(Privilege priv : this.privileges) {
			if(priv.getDelete()) {
				for(Department dept : priv.getDepts()) {
					deletableUsers.addAll(dept.getUsers());
				}
			}
		}
		return deletableUsers;
	}

	public Set<User> evalableUsers(){
		HashSet<User> evalableUsers = new HashSet<User>();

		for(Privilege priv : this.privileges) {
			if(priv.getEvaluate()) {
				for(Department dept : priv.getDepts()) {
					evalableUsers.addAll(dept.getUsers());
				}
			}
		}
		return evalableUsers;
	}    

	public Set<Department> readableDepartments(){
		HashSet<Department> readableDepts = new HashSet<Department>();

		for(Privilege priv : this.privileges) {
			if(priv.getRead()) {
				readableDepts.addAll(priv.getDepts());
			}
		}
		return readableDepts;
	}

	public Set<Department> writableDepartments(){
		HashSet<Department> writableDepts = new HashSet<Department>();

		for(Privilege priv : this.privileges) {
			if(priv.getWrite()) {
				writableDepts.addAll(priv.getDepts());
			}
		}
		return writableDepts;
	}

	public Set<Department> deletableDepartments(){
		HashSet<Department> deletableDepts = new HashSet<Department>();

		for(Privilege priv : this.privileges) {
			if(priv.getDelete()) {
				deletableDepts.addAll(priv.getDepts());
			}
		}
		return deletableDepts;
	}

	public Set<Department> evalableDepartments(){
		HashSet<Department> evalableDepts = new HashSet<Department>();

		for(Privilege priv : this.privileges) {
			if(priv.getEvaluate()) {
				evalableDepts.addAll(priv.getDepts());
			}
		}
		return evalableDepts;
	}


	public Set<Location> readableLocations(){
		HashSet<Location> readableLocations = new HashSet<Location>();

		for(Privilege priv : this.privileges) {
			if(priv.getRead()) {
				for(LocationGroup locGroup : priv.getLocationGroups()) {
					readableLocations.addAll(locGroup.getLocations());
				}
			}
		}
		return readableLocations;
	}

	public Set<Location> writableLocations(){
		HashSet<Location> writableLocations = new HashSet<Location>();

		for(Privilege priv : this.privileges) {
			if(priv.getWrite()) {
				for(LocationGroup locGroup : priv.getLocationGroups()) {
					writableLocations.addAll(locGroup.getLocations());
				}
			}
		}
		return writableLocations;
	}

	public Set<Location> deletableLocations(){
		HashSet<Location> deletableLocations = new HashSet<Location>();

		for(Privilege priv : this.privileges) {
			if(priv.getDelete()) {
				for(LocationGroup locGroup : priv.getLocationGroups()) {
					deletableLocations.addAll(locGroup.getLocations());
				}
			}
		}
		return deletableLocations;
	}

	public Set<Location> evalableLocations(){
		HashSet<Location> evalableLocations = new HashSet<Location>();

		for(Privilege priv : this.privileges) {
			if(priv.getEvaluate()) {
				for(LocationGroup locGroup : priv.getLocationGroups()) {
					evalableLocations.addAll(locGroup.getLocations());
				}
			}
		}
		return evalableLocations;
	}

	public Set<LocationGroup> readableLocationGroups(){
		HashSet<LocationGroup> readableLocGroups = new HashSet<LocationGroup>();

		for(Privilege priv : this.privileges) {
			if(priv.getRead()) {
				readableLocGroups.addAll(priv.getLocationGroups());
			}
		}
		return readableLocGroups;
	}

	public Set<LocationGroup> writableLocationGroups(){
		HashSet<LocationGroup> writableLocGroups = new HashSet<LocationGroup>();

		for(Privilege priv : this.privileges) {
			if(priv.getWrite()) {
				writableLocGroups.addAll(priv.getLocationGroups());
			}
		}
		return writableLocGroups;
	}

	public Set<LocationGroup> deletableLocationGroups(){
		HashSet<LocationGroup> deletableLocGroups = new HashSet<LocationGroup>();

		for(Privilege priv : this.privileges) {
			if(priv.getDelete()) {
				deletableLocGroups.addAll(priv.getLocationGroups());
			}
		}
		return deletableLocGroups;
	}

	public Set<LocationGroup> evalableLocationGroups(){
		HashSet<LocationGroup> evalableLocGroups = new HashSet<LocationGroup>();

		for(Privilege priv : this.privileges) {
			if(priv.getEvaluate()) {
				evalableLocGroups.addAll(priv.getLocationGroups());
			}
		}
		return evalableLocGroups;
	}

	
	

	/*
	 * checks to see which roles in a list of roles are subroles to this one. 
	 */
	public Set<Role> containsRoles(Collection<Role> roles){
		HashSet<Role> containsRoles = new HashSet<Role>();
		for(Role role : roles) {
			if(this.contains(role)) {
				containsRoles.add(role);
			}			
		}
		return containsRoles;
	}

	/*
	 * this method checks to see if the role being passed in is contained in the current role. 
	 * Essentially checking if this role has higher privilege than the role being passed in.
	 * 
	 * 	 @param Role role  	The role that is being checked for privilege level (is it lower than this role)
	 * 	
	 */
	public boolean contains(Role role) {

		if(!this.privileges.containsAll(role.getPrivileges())) {
			return true;
		}
		if(!this.readableDepartments().containsAll(role.readableDepartments())) {
			return false;
		}
		if(!this.writableDepartments().containsAll(role.writableDepartments())) {
			return false;
		}
		if(!this.deletableDepartments().containsAll(role.deletableDepartments())) {
			return false;
		}
		if(!this.evalableDepartments().containsAll(role.evalableDepartments())) {
			return false;
		}			
		if(!this.readableLocationGroups().containsAll(role.readableLocationGroups())) {
			return false;
		}
		if(!this.writableLocationGroups().containsAll(role.writableLocationGroups())) {
			return false;
		}
		if(!this.deletableLocationGroups().containsAll(role.deletableLocationGroups())) {
			return false;
		}
		if(!this.evalableLocationGroups().containsAll(role.evalableLocationGroups())) {
			return false;
		}
		if(!this.readableLocations().containsAll(role.readableLocations())) {
			return false;
		}
		if(!this.writableLocations().containsAll(role.writableLocations())) {
			return false;
		}
		if(!this.deletableLocations().containsAll(role.deletableLocations())) {
			return false;
		}
		if(!this.evalableLocations().containsAll(role.evalableLocations())) {
			return false;
		}
		if(!this.readableUsers().containsAll(role.readableUsers())) {
			return false;
		}
		if(!this.writableUsers().containsAll(role.writableUsers())) {
			return false;
		}
		if(!this.deletableUsers().containsAll(role.deletableUsers())) {
			return false;
		}
		if(!this.evalableUsers().containsAll(role.evalableUsers())) {
			return false;
		}		
		if(!this.readableCompanies().containsAll(role.readableCompanies())) {
			return false;
		}
		if(!this.writableCompanies().containsAll(role.writableCompanies())) {
			return false;
		}
		if(!this.deletableCompanies().containsAll(role.deletableCompanies())) {
			return false;
		}
		if(!this.evalableCompanies().containsAll(role.evalableCompanies())) {
			return false;
		}
		return true;
	}

	//    add or remove users/privileges. 
	public boolean addPrivilege(Privilege p) {
		this.privileges.add(p);
		return true;
	}

	public boolean removePrivilege(Privilege p) {
		if(this.privileges.contains(p)) {
			this.privileges.remove(p);
			return true;
		}
		return false;

	}

	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}

	public boolean addUser(User u) {
		this.users.add(u);
		return true;
	}

	public boolean removeUser(User u) {
		if(this.users.contains(u)) {
			this.users.remove(u);
			return true;
		}
		return false;

	}


	public boolean addCompany(Company co) {
		this.companies.add(co);
		return true;
	}

	public boolean removeCompany(Company co) {
		if(this.companies.contains(co)) {
			this.companies.remove(co);
			return true;
		}
		return false;

	}
	//getters and setters. 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}


	public boolean compareTo(Role role) {
		return (this.id ==role.id && this.name == role.name && this.privileges.equals(role.privileges) && this.users.equals(role.users));
	}




}
