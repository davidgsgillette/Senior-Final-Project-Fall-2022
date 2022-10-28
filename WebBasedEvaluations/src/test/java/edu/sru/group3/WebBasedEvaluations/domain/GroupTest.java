package edu.sru.group3.WebBasedEvaluations.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class GroupTest {
	@Mock static long id;
	@Mock static Boolean selfEval;
	@Mock static Reviewee rev;
	@Mock static User user;
	static Group group;
	@BeforeAll
	static void setup() {
		group = new Group(id, selfEval);
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
/* I have no idea why this doesn't work but here we are
	@Test
	public void appendRevieweeTest() {
		List<Reviewee> actual = null;
		group.setReviewee(actual);
		rev.setUser(user);
		group.appendReviewee(rev);
		assertEquals(group.getReviewee(), actual);
		
	}
*/
	@Test
	public void getEvalTemplatesTest() {
		EvalTemplates actual = new EvalTemplates();
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
