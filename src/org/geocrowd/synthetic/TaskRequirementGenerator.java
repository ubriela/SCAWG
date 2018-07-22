package org.geocrowd.synthetic;

import org.geocrowd.common.distribution.TaskRequirementEnum;
import org.geocrowd.dtype.Range;

/**
 * Generate the requirement each task 
 * @author ubriela
 *
 */
public class TaskRequirementGenerator {

	private int maxRequirement;
	
	public TaskRequirementGenerator(int maxRequirement) {
		this.maxRequirement = maxRequirement;
	}
	
	public int nextWorkerRequirement(TaskRequirementEnum requirementType) {
		switch(requirementType) {
			case CONSTANT:
				return maxRequirement;
			case RANDOM:
				return (int) UniformGenerator.randomValue(
						new Range(0, maxRequirement), true) + 1;
		}
		return 0;	
	}
}
