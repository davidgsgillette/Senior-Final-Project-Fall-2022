package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import edu.sru.group3.WebBasedEvaluations.company.Company;


public class GroupTest {
	@Mock static int groupNum;
	@Mock static Boolean selfEval;
	@Mock static Reviewee rev;
	@Mock static User user;
	@Mock static Company co;
	static Group group;
	@BeforeAll
	static void setup() {
		group = new Group(selfEval,co,groupNum);
	}

	@Test
	public void getIdTest() {
		long actual = 5;
		group.setId(actual);
		assertEquals(group.getId(), actual);
	}

	@Test
	public void getRevieweeTest() {
		List<Reviewee> actual = null;
		group.setReviewee(actual);
		assertNull(group.getReviewee());
	}

	@Test
	public void getEvaluatorTest() {
		List<Evaluator> actual = null;
		group.setEvaluator(actual);
		assertNull(group.getEvaluator());
	}

	@Test
	public void appendRevieweeTest() {
		List<Reviewee> actual = null;
		group.setReviewee(actual);
		rev.setUser(user);
		group.appendReviewee(rev);
		assertEquals(group.getReviewee(), actual);
		

	}

	@Test
	public void getEvalTemplatesTest() {
		EvalTemplates actual = new EvalTemplates(co);
		group.setEvalTemplates(actual);
		assertEquals(group.getEvalTemplates(), actual);
	}

	@Test
	public void getEvalstartTest() {
		group.setEvalstart(true);
		assertTrue(group.getEvalstart());
		group.setEvalstart(false);
		assertFalse(group.getEvalstart());
	}

	@Test
	public void getSelfevalTest() {
		group.setSelfeval(true);
		assertTrue(group.getSelfeval());
		group.setSelfeval(false);
		assertFalse(group.getSelfeval());
	}

}
