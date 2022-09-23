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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;





/**Class for methods of a City object, almost exclusively made out of getters and setters.
 * also includes mapping for sql tables. 
 * @author David Gillette
 *
 */
@Entity
@Table(name = "city")
public class City {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	String cityName;

	@NonNull
	private int numLocations;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province_id", nullable = false)
	private Province parentProvince;

	@NonNull
	@OneToMany(mappedBy = "parentCity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Location> childLocations;


	public City() {
		
	}
	
	
	public City(String cityName, int numLocations, Province parentProvince, List<Location> locations) {
		this.cityName = cityName;
		this.numLocations = numLocations;
		this.parentProvince = parentProvince;
		this.childLocations = locations;
	}

	public City(String cityName, int numLocations, Province parentProvince, Location location) {
		this.cityName = cityName;
		this.numLocations = numLocations;
		this.parentProvince = parentProvince;
		this.childLocations = new ArrayList<>();
		this.childLocations.add(location);
	}


	/*
	 * adds a location
	 */
	public boolean addLocation(Location loc) {
		this.childLocations.add(loc);
		return true;
	}


	/*
	 * adds a list of locations
	 */
	public boolean addLocations(List<Location> locs) {
		for(Location loc : locs) { 
			this.childLocations.add(loc);
		}
		return true;
	}
	
	/*
	 * removes a location
	 */
	public boolean removeLocation(Location loc) {
		if(this.childLocations.contains(loc)) {
			this.childLocations.add(loc);
			return true;
		}
		return false;
	}

	
	//getters and setters
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getNumLocations() {
		return numLocations;
	}

	public void setNumLocations(int numLocations) {
		this.numLocations = numLocations;
	}

	public Province getParentProvince() {
		return parentProvince;
	}

	public void setParentProvince(Province parentProvince) {
		this.parentProvince = parentProvince;
	}

	public List<Location> getChildLocations() {
		return childLocations;
	}

	public void setChildLocations(List<Location> childLocations) {
		this.childLocations = childLocations;
	}
	

	
	
	
	
}
