package edu.sru.group3.WebBasedEvaluations.repository;



import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.domain.Privilege;

public interface PrivilegeRepository extends CrudRepository<Privilege,Long>{
	
	public Privilege findByName(String name);
}
