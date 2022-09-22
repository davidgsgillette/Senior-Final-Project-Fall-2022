package edu.sru.group3.WebBasedEvaluations.repository;


import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import antlr.collections.List;
import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.User;


@Repository
public interface CompanyRepository extends CrudRepository<Company,Long>{

	
	
	public Company findByCompanyName(String companyName);
	public Company findById(long id);
	public Object findAll(Sort by);

}
