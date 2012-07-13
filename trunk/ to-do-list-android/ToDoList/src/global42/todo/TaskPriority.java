package global42.todo;

/**
 * @author
 * Dominik provides different priorities a {@link Task} can have
 */
public enum TaskPriority {
	Low(1), Normal(2), High(3);

	int priority;

	TaskPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return {@link Integer} priority id as it is saved in the database
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param {@link Integer} priority id as it is saved in the database
	 * @return {@link TaskStatus} equivalent enumeration item
	 */
	public static TaskPriority getTaskPriorityFor(int priority) {
		TaskPriority taskPriority = TaskPriority.Normal;

		for (TaskPriority currentTaskPriority : TaskPriority.values()) {
			if (currentTaskPriority.getPriority() == priority)
				taskPriority = currentTaskPriority;
		}
		return taskPriority;
	}
}
