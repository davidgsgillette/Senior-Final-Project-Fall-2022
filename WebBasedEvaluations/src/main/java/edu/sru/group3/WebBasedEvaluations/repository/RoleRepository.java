package edu.sru.group3.WebBasedEvaluations.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.Role;

public interface RoleRepository extends CrudRepository<Role,Long > {

	
	
	public Role findByNameAndCompany(String name, Company company);
	public List<Role> findByCompany(Company company);
	
}
