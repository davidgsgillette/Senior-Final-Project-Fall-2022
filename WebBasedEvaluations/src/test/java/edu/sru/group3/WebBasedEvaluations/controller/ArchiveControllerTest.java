package edu.sru.group3.WebBasedEvaluations.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import edu.sru.group3.WebBasedEvaluations.repository.ArchiveRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvalRoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationLogRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RevieweeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

@Suite
public class ArchiveControllerTest {
	
	Model model;
	Authentication auth;
	
	private GroupRepository groupRepository;

	private UserRepository userRepository;

	private EvaluatorRepository evaluatorRepository;
	private EvaluationLogRepository evaluationLogRepository;
	private RevieweeRepository revieweeRepository;
	private EvalRoleRepository roleRepository;
	private EvaluationRepository evaluationRepository;
	private EvaluationRepository evalFormRepo;
	private ArchiveRepository archiveRepository ;
	
	ArchiveController controller = new ArchiveController(archiveRepository, groupRepository, userRepository, evaluatorRepository, revieweeRepository, evaluationLogRepository, roleRepository, evaluationRepository, evalFormRepo);
	
	@Test
	public void evalGroupTest() {
		
		boolean attempt = false;
		String asorb = "";
		
		try {
			asorb = controller.evalGroups(null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && asorb.isEmpty());
		asorb = "";
		
		try {
			
			asorb = controller.evalGroups(model, auth);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !asorb.isEmpty());
		
	}
	
	@Test
	public void ViewViewArchiveTest() {
		
		boolean attempt = false;
		String asorb = "";
		
		try {
			asorb = controller.ViewViewArchive(0, null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && asorb.isEmpty());
		asorb = "";
		
		try {
			
			asorb = controller.ViewViewArchive(0, model, auth);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !asorb.isEmpty());
		
	}
		

}
