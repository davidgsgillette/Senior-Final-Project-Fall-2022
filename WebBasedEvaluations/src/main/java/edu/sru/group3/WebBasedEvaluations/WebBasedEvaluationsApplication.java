package edu.sru.group3.WebBasedEvaluations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import edu.sru.group3.WebBasedEvaluations.company.Company;
import edu.sru.group3.WebBasedEvaluations.domain.Evaluator;
import edu.sru.group3.WebBasedEvaluations.domain.Group;
import edu.sru.group3.WebBasedEvaluations.domain.Reviewee;
import edu.sru.group3.WebBasedEvaluations.domain.User;
import edu.sru.group3.WebBasedEvaluations.repository.CompanyRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebBasedEvaluationsApplication {
//adding a comment to test merge. 
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WebBasedEvaluationsApplication.class, args);
		
//		UserRepository usersr=configurableApplicationContext.getBean(UserRepository.class);
//		CompanyRepository companies=configurableApplicationContext.getBean(CompanyRepository.class);
//		Company co = new Company("admin CO");
//		companies.save(co);
//		User use1 = new User("jimmy neutron","fname","lname","admin@gmail.com","$2y$12$.ahxo5UdngIuZdKSu91Jn.VtHjjYCh04.lpM5LNFdICjEjechMDQ","ADMIN", 999991, "N/A", "N/A", "N/A", "N/A", "N/A", co);
//		//User use2 = new User("eval_admin","fname","lname","evaladmin@gmail.com","$2y$12$nUFl.ypDL1ZAeqIR9Uq5SOZmBoOTlCmcgRm2tK2.B4dZIWEdrx4u6","EVAL_ADMIN", 999992, "N/A", "N/A", "N/A", "N/A", "N/A");
//		
//		use1.setEncryptedPassword("test");
//		usersr.save(use1);
		//usersr.save(use2);

	}
	
}