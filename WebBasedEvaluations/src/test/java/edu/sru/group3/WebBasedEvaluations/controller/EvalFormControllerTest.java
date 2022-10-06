package edu.sru.group3.WebBasedEvaluations.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import edu.sru.group3.WebBasedEvaluations.evalform.Evaluation;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationLogRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;

@Suite
public class EvalFormControllerTest {
	
	EvaluationRepository evalFormRepo; 
	EvaluationLogRepository evalLogRepo; 
	GroupRepository groupRepo;
	Model model;
	MultipartFile file;
	RedirectAttributes redir;
	Evaluation eval;
	HttpStatus status;
	
	EvalFormController controller = new EvalFormController(evalFormRepo, evalLogRepo, groupRepo);
	
	@Test
	public void adminEvaluationsTest() {
		
		String result = "";
		boolean attempt = false;
		
		try {
			
			result = controller.adminEvaluations(null);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
		}
		
		assertTrue(attempt && !result.isEmpty());
		
		result = "";
		attempt = false;
		
		try {
			
			result = controller.adminEvaluations(model);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
		}
		
		assertTrue(attempt && !result.isEmpty());
	}

	@Test
	public void uploadEvalTemplateTest() {
		
		Object thing = new Object();
		boolean attempt = false;
		
		try {
			
			thing = controller.uploadEvalTemplate(null, null);
			attempt = true;
			
		}
		
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !thing.toString().isEmpty());
		
		thing = new Object();
		attempt = false;
		
		try {
			
			thing = controller.uploadEvalTemplate(file, redir);
			attempt = true;
			
		}
		
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !thing.toString().isEmpty());
		
	}

	@Test
	public void saveEvalTemplateTest() {
		
		RedirectView view = new RedirectView();
		boolean attempt = false;
		
		try {
			
			view = controller.saveEvalTemplate(null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !view.toString().isEmpty());
		
		view = new RedirectView();
		attempt = false;
		
		try {
			
			view = controller.saveEvalTemplate(eval, model);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !view.toString().isEmpty());
		
	}

	@Test
	public void downloadEvalResultsTest() {
		
		ResponseEntity<Resource> reasources = new ResponseEntity<Resource>(status);
		boolean attempt = false;
		
		try {
			reasources = controller.downloadEvalResults(null);
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
		
		reasources = new ResponseEntity<Resource>(status);
		attempt = false;
		
		try {
			reasources = controller.downloadEvalResults("1");
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
		
		
		
	}

	@Test
	public void downloadEvalExcelTest() {
		
		ResponseEntity<Resource> reasources = new ResponseEntity<Resource>(status);
		boolean attempt = false;
		
		try {
			reasources = controller.downloadEvalExcel(null);
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
		
		reasources = new ResponseEntity<Resource>(status);
		attempt = false;
		
		try {
			reasources = controller.downloadEvalExcel("1");
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
		
	}

	@Test
	public void downloadErrorLogTest() {
		
		ResponseEntity<Resource> reasources = new ResponseEntity<Resource>(status);
		boolean attempt = false;
		
		try {
			reasources = controller.downloadEvalExcel(null);
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
		
		reasources = new ResponseEntity<Resource>(status);
		attempt = false;
		
		try {
			reasources = controller.downloadEvalExcel("1");
			
		}
		catch (Exception e) {
			
			attempt = false;
		}
		
		assertTrue(attempt && !reasources.toString().isEmpty());
	}

	@Test
	public void deleteEvalTemplateTest() {
		
		RedirectView view = new RedirectView();
		boolean attempt = false;
		
		try {
			
			view = controller.deleteEvalTemplate(null, null);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !view.toString().isEmpty());
		
		view = new RedirectView();
		attempt = false;
		
		try {
			
			view = controller.deleteEvalTemplate("1", redir);
			attempt = true;
			
		}
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && !view.toString().isEmpty());
	}
}
