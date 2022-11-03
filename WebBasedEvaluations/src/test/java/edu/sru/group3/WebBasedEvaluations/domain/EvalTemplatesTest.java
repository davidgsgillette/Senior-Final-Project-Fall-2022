package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.sru.group3.WebBasedEvaluations.company.Company;

public class EvalTemplatesTest {

	static EvalTemplates evalTemplate;
	static Company company;
	
	@BeforeAll
	static void setup() {
		evalTemplate = new EvalTemplates(company);
	}
	
	@Test
	void getNameTest() {
		String actual = "Reginald";
		assertEquals(evalTemplate.getName(),null);
		evalTemplate.setName("Reginald");
		assertEquals(evalTemplate.getName(),actual);
	}
	
	@Test
	void getEvalTest(){
		byte[] actual = new byte[1];
		actual[0] = (byte) 4;
		evalTemplate.setEval(actual);
		assertEquals(evalTemplate.getEval(), actual);
		
	}
	
	@Test
	void getExcelFileTest() {
		byte[] actual = new byte[1];
		actual[0] = (byte) 8;
		evalTemplate.setExcelFile(actual);
		assertEquals(evalTemplate.getExcelFile(), actual);
	}
}
