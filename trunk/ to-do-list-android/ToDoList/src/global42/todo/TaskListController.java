/**
 * 
 */
package global42.todo;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;

/**
 * @author lutz
 *
 */
public class TaskListController{
	private Set<Task> tasks;
	private TasksDataSource dataSource;
	private static TaskListController instance;
	
	
	private TaskListController(){
		tasks = new HashSet<Task>();
	}
	
	public static TaskListController getInstance(){
		if(instance == null)
			instance = new TaskListController();
		
		return instance;
	}
	public void addTask(String title){
		Task newTask = new Task(this.dataSource,title, TaskStatus.Active, TaskPriority.Normal, new String());
		tasks.add(newTask);
	}

	public Set<Task> getTasks() {
		return tasks;
	}
}
