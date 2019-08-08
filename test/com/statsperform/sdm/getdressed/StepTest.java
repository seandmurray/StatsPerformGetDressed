package com.statsperform.sdm.getdressed;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class StepTest {

	static void checkDependencies(Step step, String... names) {
		Map<String, Boolean> check = new HashMap<String, Boolean>();
		for (String name : names) {
			check.put(name, true);
		}
		for (Step dependent : step.getDependencies()) {
			String name = dependent.getName();
			assertTrue(check.containsKey(name));
			check.remove(name);
		}
		if (check.size() > 0) {
			fail("Dependents missing: " + check.keySet().toString());
		}
	}

	@Test
	public void testCreate() {
		Step step1 = new Step("somename");
		assertEquals("somename", step1.getName());
	}

	@Test
	public void testDependencies() {
		Step step1 = new Step("step1");
		Step step2 = new Step("step2");
		Step step3 = new Step("step3");
		step3.addDependencies(step1, step2);
		checkDependencies(step3, "step1", "step2");
	}

	@Test
	public void testIncompleteDependencies() {
		Step step1 = new Step("step1");
		Step step2 = new Step("step2");
		Step step3 = new Step("step3");
		Step step4 = new Step("step4");
		step2.addDependencies(step1);
		step3.addDependencies(step1, step2);
		step4.addDependencies(step1, step2, step3);

		// try to do out of dependent order
		assertFalse(step3.complete());
		assertFalse(step2.complete());

		assertTrue(step1.complete());
		assertTrue(step2.complete());
		// 1 & 2 are complete but 3 is not
		assertFalse(step4.complete());
	}

	@Test
	public void testCompleteDependencies() {
		Step step1 = new Step("step1");
		Step step2 = new Step("step2");
		Step step3 = new Step("step3");
		step2.addDependencies(step1);
		step3.addDependencies(step1, step2);
		assertTrue(step1.complete());
		assertTrue(step2.complete());
		assertTrue(step3.complete());
	}

}
