package global42.todo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lutz 
 * manages the whole task list
 */
public class TaskListController {
	private Set<Task> tasks;
	private static TaskListController instance;

	private TaskListController() {
		tasks = new HashSet<Task>();
	}

	/**
	 * @return instance of the singleton
	 */
	public static TaskListController getInstance() {
		if (instance == null)
			instance = new TaskListController();
		return instance;
	}

	/**
	 * quick add a {@link Task} with default values
	 * @param {@link String} title of the task to be added
	 */
	public void addTask(String title) {
		Task newTask = new Task(title, TaskStatus.Active, TaskPriority.Normal, new String());
		tasks.add(newTask);
	}

	/**
	 * @return {@link Set} of all {@link Task}s
	 */
	public Set<Task> getTasks() {
		return tasks;
	}
}
