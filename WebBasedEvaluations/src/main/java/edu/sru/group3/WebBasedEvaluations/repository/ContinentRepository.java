package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Continent;

public interface ContinentRepository extends CrudRepository<Continent,Long>{
	
	public Continent findByContinentName(String continentName);
	public Continent findById(long id);
	public Object findAll(Sort by);
	
}
