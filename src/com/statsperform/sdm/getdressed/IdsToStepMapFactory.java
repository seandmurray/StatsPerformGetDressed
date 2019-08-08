package com.statsperform.sdm.getdressed;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

public class IdsToStepMapFactory {

	private static final IdsToStepMapFactory SINGLETON = new IdsToStepMapFactory();

	public static Map<Integer, Step> convert(Properties properties) {
		return SINGLETON._convert(properties);
	}

	private static final String SEPERATOR_STEP_DEPENDENCIES = ",";

	private IdsToStepMapFactory() {
	}

	private Map<Integer, Step> _convert(Properties properties) {
		// TODO check for circular dependencies.
		Map<Integer, Step> idMapStep = new HashMap<Integer, Step>();
		Map<Integer, StepDependencyids> IdStepMapWithDependencyids = this.getIdStepMapWithDependencyids(properties);
		for (Entry<Integer, StepDependencyids> entry : IdStepMapWithDependencyids.entrySet()) {
			Step step = entry.getValue().step;
			for (Integer dependencyid : entry.getValue().dependencyIds) {
				Step stepDependency = IdStepMapWithDependencyids.get(dependencyid).step;
				step.addDependencies(stepDependency);
			}
			idMapStep.put(entry.getKey(), step);
		}
		return idMapStep;
	}

	private Map<Integer, StepDependencyids> getIdStepMapWithDependencyids(Properties properties) {
		Map<Integer, StepDependencyids> IdStepMapWithDependencyids = new HashMap<Integer, StepDependencyids>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			IdStepMapWithDependencyids.put(Integer.valueOf((String) entry.getKey()),
					new StepDependencyids((String) entry.getValue()));
		}
		return IdStepMapWithDependencyids;
	}

	private class StepDependencyids {
		Step step = null;
		final Set<Integer> dependencyIds = new HashSet<Integer>();

		StepDependencyids(String dependencyIdsStr) {
			String[] nameDependencies = dependencyIdsStr.split(SEPERATOR_STEP_DEPENDENCIES);
			for (int i = 0; i < nameDependencies.length; i++) {
				if (i == 0) {
					this.step = new Step(nameDependencies[i]);
				} else {
					dependencyIds.add(Integer.parseInt(nameDependencies[i]));
				}
			}
		}
	}

}
