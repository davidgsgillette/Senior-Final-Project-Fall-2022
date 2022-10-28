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

/**Class for methods of a Country object, almost exclusively made out of getters and setters.
 * also includes mapping for sql tables. 
 * @author David Gillette
 *
 */
@Entity
@Table(name = "country")
public class Country {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	String countryName;

	@NonNull
	private int numProvinces;

	@NonNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "continent_id", nullable = false)
	private Continent parentContinent;

	@NonNull
	@OneToMany(mappedBy = "parentCountry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Province> childProvinces;
	
	
	public Country() {
		
	}
	
	public Country(String countryName, Continent parentContinent) {
		this.countryName = countryName;
		this.numProvinces = 0;
		this.parentContinent = parentContinent;
		this.childProvinces = new ArrayList<Province>();
	}
	
	public Country(String countryName, int numProvinces, Continent parentContinent, List<Province> childProvinces) {
		this.countryName = countryName;
		this.numProvinces = numProvinces;
		this.parentContinent = parentContinent;
		this.childProvinces = childProvinces;
	}

	public Country(String countryName, int numProvinces, Continent parentContinent, Province childProvince) {
		this.countryName = countryName;
		this.numProvinces = numProvinces;
		this.parentContinent = parentContinent;
		this.childProvinces = new ArrayList<Province>();
		this.childProvinces.add(childProvince);
	}
	
	
	
	/*
	 * adds a location
	 */
	public boolean addProvince(Province province) {
		this.childProvinces.add(province);
		this.numProvinces++;
		return true;
	}


	/*
	 * adds a list of locations
	 */
	public boolean addProvinces(List<Province> provinces) {
		
		this.childProvinces.addAll(provinces);
		setNumProvinces(provinces.size());
		
		return true;
	}
	
	/*
	 * removes a province, should not be needed though. 
	 */
	public boolean removeProvince(Province province) {
		if(this.childProvinces.contains(province)) {
			this.childProvinces.remove(province);
			this.numProvinces--;
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

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getNumProvinces() {
		return numProvinces;
	}

	public void setNumProvinces(int numProvinces) {
		this.numProvinces = numProvinces;
	}

	public Continent getParentContinent() {
		return parentContinent;
	}

	public void setParentContinent(Continent parentContinent) {
		this.parentContinent = parentContinent;
	}

	public List<Province> getChildProvinces() {
		return childProvinces;
	}

	public void setChildProvinces(List<Province> childProvinces) {
		this.childProvinces = childProvinces;
	}
	
	
	
}
