package edu.sru.group3.WebBasedEvaluations.domain;

import lombok.NonNull;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import edu.sru.group3.WebBasedEvaluations.company.Department;
import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;



/*
 * This class is adapted from https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 * @Author David Gillette
 * This class holds the specific privileges that can be given to a role. they include read, write, delete (RWD) and 
 * what group they have this power over. 
 * 
 * contains a lists of departments and location groups that this privilege has the given RWD over. 
 */
@Entity
@Table(name = "privilege")
public class Privilege {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
		
	//read
	@NonNull
	private boolean r;
		
	//write
	@NonNull
	private boolean w;
		
	//delete
	@NonNull
	private boolean d;
	
	@NonNull
	private boolean evaluate;

	
	
	@NonNull
	@Column(unique=true)
    private String name;
	
	@ManyToMany(mappedBy = "privileges")
    private List<Role> roles;   
    
    
    @ManyToMany
	@JoinTable(
			name = "privilege_dept", 
			joinColumns = @JoinColumn(name = "privilege_id"), 
			inverseJoinColumns = @JoinColumn(name = "dept_id"))
    private List<Department> depts;
    
    
    @ManyToMany
	@JoinTable(
			name = "privilege_location_group", 
			joinColumns = @JoinColumn(name = "privilege_id"), 
			inverseJoinColumns = @JoinColumn(name = "location_group_id"))
    private List<LocationGroup> locationGroups;
    
   
    public Privilege() {
    	
    }
    
    
    public Privilege(String name, Role role, boolean read, boolean write, boolean delete, boolean evaluate) {
    	this.name = name;
    	this.roles = new ArrayList<Role>();
    	this.roles.add(role);
    	this.depts = new ArrayList<Department>();
    	this.locationGroups = new ArrayList<LocationGroup>(); 
    	this.r = read;
    	this.w = write;
    	this.d = delete;
    	this.evaluate = evaluate;
    }
    
    
	public Privilege(String name, Role role, LocationGroup locGroup, Department dept, boolean read, boolean write, boolean delete, boolean evaluate) {
    	this.name = name;
    	this.roles = new ArrayList<Role>();
    	this.roles.add(role);
    	this.depts = new ArrayList<Department>();
    	this.locationGroups = new ArrayList<LocationGroup>();
    	this.depts.add(dept);
    	this.locationGroups.add(locGroup);
    	this.r = read;
    	this.w = write;
    	this.d = delete;
    	this.evaluate = evaluate;
    }
	
	
    public Privilege(String name, List<Role> roles, List<LocationGroup> locGroups, List<Department> depts, boolean read,boolean write,boolean delete, boolean evaluate) {
    	this.name = name;
    	this.roles = roles;
    	this.locationGroups = locGroups;
    	this.depts = depts;
    	this.r = read;
    	this.w = write;
    	this.d = delete;
    	this.evaluate = evaluate;
    }
    
    
    public void addLocGroup(LocationGroup locGroup) {
		this.locationGroups.add(locGroup);
	}
	
	public boolean removeLocGroup(LocationGroup locGroup) {
		if(this.locationGroups.contains(locGroup)) {
			this.locationGroups.remove(locGroup);
			return true;
		}
		return false;
	}
    
    
    public void addDept(Department dept) {
		this.depts.add(dept);
	}
	
	public boolean removeDept(Department dept) {
		if(this.depts.contains(dept)) {
			this.depts.remove(dept);
			return true;
		}
		return false;
	}
    
    public boolean addRole(Role role) {
    	this.roles.add(role);
    	return true;
    }
    
    public boolean removeRole(Role role) {
    	if(this.roles.contains(role)) {
    		this.roles.remove(role);
    		return true;
    	}
    	return false;
    }
    
    
    //getters and setters.
    
    public boolean getRead() {
		return r;
	}
	public void setRead(boolean read) {
		this.r = read;
	}
	public boolean getWrite() {
		return w;
	}
	public void setWrite(boolean write) {
		this.w = write;
	}
	public boolean getDelete() {
		return d;
	}
	public void setDelete(boolean delete) {
		this.d = delete;
	}
	public List<Department> getDepts() {
		return depts;
	}
	public void setDepts(List<Department> depts) {
		this.depts = depts;
	}
	public List<LocationGroup> getLocationGroups() {
		return locationGroups;
	}
	public void setLocationGroups(List<LocationGroup> locationGroups) {
		this.locationGroups = locationGroups;
	}
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
    
    
    
    
    
    
	
    
}
