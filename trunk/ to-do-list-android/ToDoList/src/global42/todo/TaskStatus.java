package global42.todo;

/**
 * @author Dominik
 * provides different status a {@link Task} can have
 */
public enum TaskStatus {
	Undefined(0), Active(1), Done(2), Deleted(3);

	private int status;

	TaskStatus(int status) {
		this.status = status;
	}

	/**
	 * @return {@link Integer} status id as it is saved in the database
	 */
	public int getStatusId() {
		return this.status;
	}

	/**
	 * @param {@link Integer} status id as it is saved in the database
	 * @return {@link TaskStatus} equivalent enumeration item
	 */
	public static TaskStatus getTaskStatusFor(int status) {
		TaskStatus taskStatus = Undefined;

		for (TaskStatus currentTaskStatus : TaskStatus.values()) {
			if (currentTaskStatus.getStatusId() == status)
				taskStatus = currentTaskStatus;
		}
		return taskStatus;
	}

}
