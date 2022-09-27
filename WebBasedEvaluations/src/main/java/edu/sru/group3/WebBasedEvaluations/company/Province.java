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


/**Class for methods of a Province object, almost exclusively made out of getters and setters.
 * also includes mapping for sql tables. 
 * @author David Gillette
 *
 */
@Entity
@Table(name = "province")
public class Province {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	String provinceName;

	@NonNull
	private int numCities;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	private Country parentCountry;

	@NonNull
	@OneToMany(mappedBy = "parentProvince", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<City> childCities;


	public Province() {
		
	}
	
	public Province(String provinceName, Country parentCountry) {
		this.provinceName = provinceName;
		this.numCities = 0;
		this.parentCountry = parentCountry;
		this.childCities = new ArrayList<City>();;
	}
	
	
	public Province(String provinceName, int numCities, Country parentCountry, List<City> cities) {
		this.provinceName = provinceName;
		this.numCities = numCities;
		this.parentCountry = parentCountry;
		this.childCities = cities;
	}

	public Province(String provinceName, int numCities, Country parentCountry, City city) {
		this.provinceName = provinceName;
		this.numCities = numCities;
		this.parentCountry = parentCountry;
		this.childCities = new ArrayList<City>();
		this.childCities.add(city);
	}


	/*
	 * adds a location
	 */
	public boolean addCity(City city) {
		this.childCities.add(city);
		this.numCities++;
		return true;
	}


	/*
	 * adds a list of locations
	 */
	public boolean addCities(List<City> cities) {
		for(City city : cities) { 
			this.numCities++;
			this.childCities.add(city);
		}
		return true;
	}
	
	/*
	 * removes a city, should not be needed though. 
	 */
	public boolean removeCity(City city) {
		if(this.childCities.contains(city)) {
			this.childCities.remove(city);
			this.numCities--;			
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getNumCities() {
		return numCities;
	}

	public void setNumCities(int numCities) {
		this.numCities = numCities;
	}

	public Country getParentCountry() {
		return parentCountry;
	}

	public void setParentCountry(Country parentCountry) {
		this.parentCountry = parentCountry;
	}

	public List<City> getChildCities() {
		return childCities;
	}

	public void setChildCities(List<City> childCities) {
		this.childCities = childCities;
	}
	
	
	
}
