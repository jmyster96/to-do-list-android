/**
 * 
 */
package global42.todo;

/**
 * @author Dominik
 *
 */
public enum TaskPriority {
	Low(1),
	Normal(2),
	High(3);
	
	int priority;
	
	TaskPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return this.priority;
	}

	public static TaskPriority getTaskPriorityFor(int priority) {
		TaskPriority taskPriority = TaskPriority.Normal;
		
		for (TaskPriority currentTaskPriority : TaskPriority.values()) {
			if(currentTaskPriority.getPriority() == priority)
				taskPriority = currentTaskPriority;
		}
		return taskPriority;
	}
}
