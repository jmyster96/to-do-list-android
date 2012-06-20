/**
 * 
 */
package global42.todo;

/**
 * @author Dominik
 *
 */
public enum TaskStatus {
	Undefined(0),
	Active(1),
	Done(2),
	Deleted(3);
	
	private int status;
	
	TaskStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public static TaskStatus getTaskStatusFor(int status) {
		TaskStatus taskStatus = Undefined;
		
		for (TaskStatus currentTaskStatus : TaskStatus.values()) {
			if(currentTaskStatus.getStatus() == status)
				taskStatus = currentTaskStatus;
		}
		return taskStatus;
	}
}
