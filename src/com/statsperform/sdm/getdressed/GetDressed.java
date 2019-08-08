package com.statsperform.sdm.getdressed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetDressed {
	private static final Integer EXPECTED_LAST_ID = 6;
	private static final String FAIL = "fail";
	private static final String SEPERATOR_STEPS = ", ";
	private static final Map<Integer, Step> IDS_TO_STEP_MAP = IdsToStepMapFactory.convert(Config.get());

	public static void main(String... args) {
		Integer[] ids = new Integer[args.length];
		for (int i = 0; i < args.length; i++) {
			ids[i] = Integer.parseInt(args[i]);
		}
		System.out.println(go(ids));
	}

	public static String go(Integer... stepIds) {
		if (duplicates(stepIds)) {
			return FAIL;
		}
		List<String> steplist = new ArrayList<String>();
		Integer lastId = null;
		for (Integer id : stepIds) {
			lastId = id;
			Step step = IDS_TO_STEP_MAP.get(id);
			if (null == step) {
				return fail(steplist);
			}
			if (false == step.complete()) {
				return fail(steplist);
			}
			steplist.add(step.getName());
		}
		if (EXPECTED_LAST_ID != lastId) {
			return fail(steplist);
		}
		return done(steplist);
	}

	private static String fail(List<String> steplist) {
		steplist.add(FAIL);
		return done(steplist);
	}

	private static String done(List<String> steplist) {
		return String.join(SEPERATOR_STEPS, steplist);
	}

	private static boolean duplicates(Integer... ids) {
		Set<Integer> unique = new HashSet<Integer>(Arrays.asList(ids));
		if (unique.size() == ids.length) {
			return false;
		}
		return true;
	}

}
