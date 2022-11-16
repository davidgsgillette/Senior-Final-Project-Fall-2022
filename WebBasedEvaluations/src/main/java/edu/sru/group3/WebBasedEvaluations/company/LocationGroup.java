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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import edu.sru.group3.WebBasedEvaluations.domain.Privilege;

/*
 * Class for "regions", essentially groups of locations. mapped ot a sql tables. 
 * @Author david gillette
 */
@Entity
@Table(name = "location_group")
public class LocationGroup {
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@NonNull
	private String name;
	
	@OneToMany(mappedBy = "locGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Location> locations;
	
	@ManyToMany
	@JoinTable(
			name = "privilege_location_group", 
			joinColumns = @JoinColumn(name = "location_group_id"), 
			inverseJoinColumns = @JoinColumn(name = "privilege_id"))	
    private List<Privilege> privileges;
	
	
	@NonNull
	private int numLocations;
	
	/**
	 *  default constructor
	 */
	public LocationGroup() {
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
	}
	
	
	/**
	 * @param name name of location group ex: "eastUS"
	 */
	public LocationGroup(String name) {
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
	}
	
	
	/**
	 * @param locations locations to add
	 * @param privileges privileges to add
	 * @param name name of locationgroup
	 */
	public LocationGroup(List<Location> locations, List<Privilege> privileges, String name) {
		this.name = name;
		this.locations = locations;
		this.privileges = privileges;
		this.numLocations = this.locations.size();
	}
	
	/**
	 * @param location to add
	 * @param privilege to add
	 * @param name of locGroup
	 */
	public LocationGroup(Location location, Privilege privilege, String name) {
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
		this.addLocation(location);
		this.privileges = new ArrayList<Privilege>();
		this.privileges.add(privilege);
		
	}
	
	
	/**
	 * @param locations to add
	 */
	public LocationGroup(List<Location> locations) {
		this.locations = locations;
		this.numLocations = this.locations.size();
	}
	
	/**
	 * @param location to add
	 */
	public LocationGroup(Location location) {
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
		this.addLocation(location);
		
	}
	
	
	/**
	 * @param priv to add
	 */
	public void addPrivilege(Privilege priv) {
		this.privileges.add(priv);
	}
	
	/**
	 * @param priv to remove
	 * @return true if removed
	 */
	public boolean removePrivilege(Privilege priv) {
		if(this.privileges.contains(priv)) {
			this.privileges.remove(priv);
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * @param loc to add
	 */
	public void addLocation(Location loc) {
		this.locations.add(loc);
		numLocations++;
	}
	
	/**
	 * @param loc to remove
	 * @return true if removed
	 */
	public boolean removeLocation(Location loc) {
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			this.numLocations--;
			return true;
		}
		return false;
	}
	
	/**
	 * @param locations to add
	 */
	public void addLocations(List<Location> locations) {
		for(Location loc : locations) {
			locations.add(loc);
			this.numLocations++;
		}
	}
	
	
	//getters and setters. 

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


	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public int getNumLocations() {
		return numLocations;
	}

	public void setNumLocations(int numLocations) {
		this.numLocations = numLocations;
	}
	
	
	
	
}
