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

/**Class for methods of a Continent object, almost exclusively made out of getters and setters.
 * also includes mapping for sql tables. 
 * @author David Gillette
 *
 */
@Entity
@Table(name = "continent")
public class Continent {

	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	String continentName;

	@NonNull
	private int numCountries;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "continent_id", nullable = false)
	private World parentWorld;

	@NonNull
	@OneToMany(mappedBy = "parentContinent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Country> childCountries;
	
	
	public Continent() {
		
	}
	
	public Continent(String continentName, int numCountries, World parentWorld, List<Country> childCountries) {
		this.continentName = continentName;
		this.numCountries = numCountries;
		this.parentWorld = parentWorld;
		this.childCountries = childCountries;
	}

	public Continent(String continentName, int numCountries, World parentWorld, Country childCountry) {
		this.continentName = continentName;
		this.numCountries = numCountries;
		this.parentWorld = parentWorld;
		this.childCountries = new ArrayList<>();
		this.childCountries.add(childCountry);
	}
	
	
	
	/*
	 * adds a location
	 */
	public boolean addProvince(Country childCountry) {
		this.childCountries.add(childCountry);
		return true;
	}


	/*
	 * adds a list of locations
	 */
	public boolean addProvinces(List<Country> childCountries) {
		for(Country childCountry : childCountries) { 
			this.childCountries.add(childCountry);
		}
		return true;
	}
	
	/*
	 * removes a country, should not be needed though. 
	 */
	public boolean removeProvince(Country childCountry) {
		if(this.childCountries.contains(childCountry)) {
			this.childCountries.add(childCountry);
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

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public int getNumCountries() {
		return numCountries;
	}

	public void setNumCountries(int numCountries) {
		this.numCountries = numCountries;
	}

	public World getParentWorld() {
		return parentWorld;
	}

	public void setParentWorld(World parentWorld) {
		this.parentWorld = parentWorld;
	}

	public List<Country> getChildCountries() {
		return childCountries;
	}

	public void setChildCountries(List<Country> childCountries) {
		this.childCountries = childCountries;
	}
	
	
	
}
