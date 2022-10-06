package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Country;

public interface CountryRepository extends CrudRepository<Country,Long>{

	
	public Country findByCountryName(String countryName);
	public Country findById(long id);
	public Object findAll(Sort by);
	
	
}
