package edu.sru.group3.WebBasedEvaluations.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.EvalRole;
import edu.sru.group3.WebBasedEvaluations.domain.EvalTemplates;


public interface EvaluationRepository extends CrudRepository<EvalTemplates,String > {
    List<EvalTemplates> findByNameIn(List<String> names);
    List<EvalTemplates> findByCompany(Company company);
    long count();
	EvalTemplates findByNameAndCompany(String name, Company company);
	



}
