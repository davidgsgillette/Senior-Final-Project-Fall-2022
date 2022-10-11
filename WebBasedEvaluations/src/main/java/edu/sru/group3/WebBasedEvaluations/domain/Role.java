package edu.sru.group3.WebBasedEvaluations.domain;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import edu.sru.group3.WebBasedEvaluations.company.Company;


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
	
	
    @OneToMany(mappedBy = "role")
    private List<User> users;	

	@ManyToMany
    @JoinTable(
            name = "roles_privileges", 
            joinColumns = @JoinColumn(name = "role_id"), 
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private List<Privilege> privileges;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Company> companies;
	
	public Role() {
		 
	}
	 
    public Role(String name) {
    	this.name = name;
    }
    
    public Role(String name, User user, Privilege priv) {
    	this.name = name;
    	this.privileges = new ArrayList<Privilege>();
    	this.privileges.add(priv);
    	this.users = new ArrayList<User>();
    	this.users.add(user);
    }
    
    public Role(String name, List<User> users, List<Privilege> priv) {
    	this.name = name;
    	this.privileges = priv;
    	this.users = users;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}
	
    
    public boolean compareTo(Role role) {
    	return (this.id ==role.id && this.name == role.name && this.privileges.equals(role.privileges) && this.users.equals(role.users));
	}
	
    
    
	
}
