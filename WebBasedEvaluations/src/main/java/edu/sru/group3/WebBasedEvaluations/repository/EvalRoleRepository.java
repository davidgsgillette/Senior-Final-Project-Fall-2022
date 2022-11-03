package edu.sru.group3.WebBasedEvaluations.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.group3.WebBasedEvaluations.domain.Reviewee;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.EvalRole;

@Repository
public interface EvalRoleRepository extends CrudRepository<EvalRole,Integer>{

	EvalRole findByNameAndCompany(String name,Company co);
	List<EvalRole> findByCompany(Company company);


}
