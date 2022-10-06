package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Location;

public interface LocationRepository extends CrudRepository<Location,Long>{

	
	public Location findByLocationName(String locationName);
	public Location findById(long id);
	public Object findAll(Sort by);

}
