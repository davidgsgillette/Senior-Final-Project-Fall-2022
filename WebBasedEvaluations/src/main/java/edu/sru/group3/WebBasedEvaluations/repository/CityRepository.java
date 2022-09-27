package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.City;

public interface CityRepository extends CrudRepository<City,Long>{

	
	public City findByCityName(String cityName);
	public City findById(long id);
	public Object findAll(Sort by);

}
