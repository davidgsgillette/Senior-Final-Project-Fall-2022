package edu.sru.group3.WebBasedEvaluations.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import edu.sru.group3.WebBasedEvaluations.repository.EvalRoleRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationLogRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.EvaluatorRepository;
import edu.sru.group3.WebBasedEvaluations.repository.GroupRepository;
import edu.sru.group3.WebBasedEvaluations.repository.RevieweeRepository;
import edu.sru.group3.WebBasedEvaluations.repository.SelfEvaluationRepository;
import edu.sru.group3.WebBasedEvaluations.repository.UserRepository;

@Suite
public class DataVisualizationControllerTest {
	
	Model model;
	Authentication auth;
	HttpServletResponse responce;
	HttpServletRequest request;
	HttpStatus status;
	
	private UserRepository userRepository;
	private EvaluationRepository evalFormRepo;
	private EvaluatorRepository evaluatorRepository;
	private EvalRoleRepository RoleRepository;
	private EvaluationLogRepository evaluationLogRepository;
	private GroupRepository groupRepository;
	private RevieweeRepository revieweeRepository;
	private SelfEvaluationRepository selfEvalRepo;
	
	DataVisualizationController controller = new DataVisualizationController(userRepository, evalFormRepo, evaluatorRepository, evaluationLogRepository, groupRepository, RoleRepository, revieweeRepository, selfEvalRepo);
	
	@Test
	public void showSQLPageTest() {
		
		boolean attempt = false;
		String result = "";
		
		try {
			
			controller.showSQLPage(1, null);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && !result.isEmpty());
		
		attempt = false;
		result = "";
		
		try {
			
			controller.showSQLPage(1, model);
			attempt = true;
			
		}
		catch (Exception e) {
			attempt = false;
			
		}
		
		assertTrue(attempt && !result.isEmpty());
		
	}

	@Test
	public void handlePieChartSQLTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handlePieChartSQL(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handlePieChartSQL(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
	}
	
	@Test
	public void handleRingChartSQLTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleRingChartSQL(0, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleRingChartSQL(1, responce);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
	}
	
	@Test
	public void handleBarChartTest() {
		boolean attempt = false;
		
		try {
			
			controller.handlebarChartSQL(0, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handlebarChartSQL(1, responce);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleAreaChartTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleareaChartSQL(0, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleareaChartSQL(1, responce);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handlePieChartSQLIndividualTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handlePieChartSQLIndividual(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handlePieChartSQLIndividual(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
	}

	public void handleRingChartSQLIndividualTest() {
		
		
		boolean attempt = false;
		
		try {
			
			controller.handleRingChartSQLIndividual(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleRingChartSQLIndividual(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
	}

	@Test
	public void handleBarChartSQLIndividualTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleBarChartSQLIndividual(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleBarChartSQLIndividual(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
	}
	
	@Test
	public void handleBarAreaSQLIndividualTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleBarAreaSQLIndividual(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleBarAreaSQLIndividual(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handlePieChartGroupSQLTest() {
	
		boolean attempt = false;
		
		try {
			
			controller.handlePieChartGroupSQL(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handlePieChartGroupSQL(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleRingChartGroupSQLTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleRingChartGroupSQL(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleRingChartGroupSQL(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleBarChartGroupSQLTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleBarChartGroupSQL(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleBarChartGroupSQL(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleAreaChartGroupSQLTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleareaChartGroupSQL(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleareaChartGroupSQL(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handlePieChartSelfTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handlePieChartSelf(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handlePieChartSelf(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleRingChartSelf() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleRingChartSelf(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleRingChartSelf(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleBarChartSelf() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleBarChartSelf(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleBarChartSelf(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handleAreaChartSelfTest() {
		
		boolean attempt = false;
		
		try {
			
			controller.handleAreaChartSelf(0, null, null);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
		
		attempt = false;
		
		try {
			
			controller.handleAreaChartSelf(1, responce, model);
			attempt = true;
		}
		
		catch (Exception e){

			attempt = false;
			
		}
		
		assertTrue(attempt);
	}
	
	@Test
	public void handlePdfReportsTest() {
		
		boolean attempt = false;
		ResponseEntity<Resource> resources = new ResponseEntity<Resource>(status);
		
		try {
			
			resources = controller.handlePdfReports(1, null, null, null);
			attempt = true;
		}
		
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && resources.getStatusCodeValue() == 1);
		
		attempt = false;
		resources = new ResponseEntity<Resource>(status);
		
		try {
			
			resources = controller.handlePdfReports(1, responce, model, request);
			attempt = true;
		}
		
		catch (Exception e) {
			
			attempt = false;
			
		}
		
		assertTrue(attempt && resources.getStatusCodeValue() == 1);
	}
}
