package edu.sru.group3.WebBasedEvaluations.company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

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
	@OneToMany(mappedBy = "locGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Location> locations;
	
	@NonNull
	private int numLocations;
	
	public LocationGroup() {
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
	}
	
	
	public LocationGroup(List<Location> locations) {
		this.locations = locations;
		this.numLocations = this.locations.size();
	}
	
	public LocationGroup(Location location) {
		this.locations = new ArrayList<Location>();
		this.numLocations = 0;
		this.addLocation(location);
		
	}
	
	
	public void addLocation(Location loc) {
		this.locations.add(loc);
		numLocations++;
	}
	
	public boolean removeLocation(Location loc) {
		if(this.locations.contains(loc)) {
			this.locations.remove(loc);
			this.numLocations--;
			return true;
		}
		return false;
	}
	
	public void addLocations(List<Location> locations) {
		for(Location loc : locations) {
			locations.add(loc);
			this.numLocations++;
		}
	}
	
	
	//getters and setters. 

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
