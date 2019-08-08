package com.statsperform.sdm.getdressed;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;

public class IdsToStepMapFactoryTest {

	@Test
	public void test() {
		Properties properties = new Properties();
		properties.put("1", "one");
		properties.put("2", "two,1");
		properties.put("3", "three,1,2");
		Map<Integer, Step> idsToStepMap = IdsToStepMapFactory.convert(properties);
		Step step;

		step = idsToStepMap.get(1);
		assertEquals("one", step.getName());
		assertEquals(0, step.getDependencies().size());

		step = idsToStepMap.get(2);
		assertEquals("two", step.getName());
		assertEquals(1, step.getDependencies().size());
		StepTest.checkDependencies(step, "one");

		step = idsToStepMap.get(3);
		assertEquals("three", step.getName());
		assertEquals(2, step.getDependencies().size());
		StepTest.checkDependencies(step, "one", "two");
	}

}
