package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.World;


public interface WorldRepository extends CrudRepository<World,Long>{

	

	public World findById(long id);
	public Object findAll(Sort by);
	public World findByName(String name);
	
}
