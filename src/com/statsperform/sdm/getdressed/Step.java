package com.statsperform.sdm.getdressed;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Step {
	private boolean complete = false;
	private final Set<Step> dependencies = new HashSet<Step>();
	private final String name;

	public Step(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void addDependencies(Step... dependencies) {
		this.dependencies.addAll(Arrays.asList((dependencies)));
	}

	public boolean isComplete() {
		return this.complete;
	}

	public boolean complete() {
		for (Step dependency : this.dependencies) {
			if (false == dependency.isComplete()) {
				return false;
			}
		}
		this.complete = true;
		return true;
	}

	Set<Step> getDependencies() {
		return this.dependencies;
	}
	
}
