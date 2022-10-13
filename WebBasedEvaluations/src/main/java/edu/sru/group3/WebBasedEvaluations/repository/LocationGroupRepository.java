package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.LocationGroup;


public interface LocationGroupRepository extends CrudRepository<LocationGroup,Long>{

	public LocationGroup findById(long id);
	public Object findAll(Sort by);
	
}
