package edu.sru.group3.WebBasedEvaluations.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.company.Department;


public interface DepartmentRepository extends CrudRepository<Department,Long>{
	
	public Department findByNameAndCompany(String name, Company company);
	public Department findById(long id);
	public Object findAll(Sort by);
	
}


