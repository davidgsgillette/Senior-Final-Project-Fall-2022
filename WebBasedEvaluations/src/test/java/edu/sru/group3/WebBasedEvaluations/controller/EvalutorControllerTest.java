package edu.sru.group3.WebBasedEvaluations.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.group3.WebBasedEvaluations.evalform.Evaluation;
import edu.sru.group3.WebBasedEvaluations.repository.EvalRoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationLogRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RevieweeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

@Suite
public class EvalutorControllerTest {
	
	EvaluatorRepository evaluatorRepository;
	UserRepository userRepository;
	EvaluationLogRepository evaluationLogRepository;
	RevieweeRepository revieweeRepository;
	EvaluationRepository evalFormRepoprivate;
	EvalRoleRepository roleRepository;
	EvaluationRepository  evalFormRepo;
	Authentication auth;
	Model model;
	RedirectAttributes redir;
	HttpStatus status;
	MultipartFile file;
	Evaluation eval;
	
	EvaluatorController controller = new EvaluatorController(evaluatorRepository, userRepository, evaluationLogRepository, revieweeRepository, evalFormRepo, roleRepository, evalFormRepo);

	@Test
	public void submitEvalTest() {
		
		boolean attempt = false;
		Object thing = new Object();
		
		try {
			
			thing = controller.submiteval(1, null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && thing.toString().isEmpty());
		
		attempt = false;
		thing = new Object();
		
		try {
			
			thing = controller.submiteval(1, auth, model);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && thing.toString().isEmpty());
		
	}

	@Test
	public void previewEvalTest() {
		
		boolean attempt = false;
		Object thing = new Object();
		
		try {
			
			thing = controller.previeweval(null, 1, null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && thing.toString().isEmpty());
		
		attempt = false;
		thing = new Object();
		
		try {
			
			thing = controller.previeweval(redir, 1, auth, model);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && thing.toString().isEmpty());
		
	}

	@Test
	public void downloadTest() {
		
		ResponseEntity<byte[]> bytes = new ResponseEntity<byte[]>(status);
		boolean attempt = false;
		
		try {
			
			bytes = controller.download(null, 1, null, null);
			attempt = true;
			
		}
		
		catch (Exception e) {
			attempt = false;
		}
		
		assertTrue(attempt && bytes.toString().isEmpty());
		
		bytes = new ResponseEntity<byte[]>(status);
		attempt = false;
		
		try {
			
			bytes = controller.download(redir, 1, auth, model);
			attempt = true;
			
		}
		
		catch (Exception e) {
			attempt = false;
		}
		
		assertTrue(attempt && bytes.toString().isEmpty());
	}

	@Test
	public void saveEvalFormTest() {
		String[] strings = new String[5];
		String result = "";;
		boolean attempt = false;
		
		try {
			
			result = controller.saveEvalForm(eval, strings, attempt, 0, auth, redir, file);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && result.isEmpty());
		
		result = "";;
		attempt = false;
		
		try {
			
			result = controller.saveEvalForm(eval, strings, attempt, 0, auth, redir, file);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && result.isEmpty());
		
	}
}
