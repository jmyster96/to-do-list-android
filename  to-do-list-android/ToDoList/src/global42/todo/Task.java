/**
 * 
 */
package global42.todo;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Dominik
 *
 */
public class Task implements Serializable {
	private static final long serialVersionUID = 7940415611619861609L;
	
	private long id;
	private String title = "";
	private Date createdOnDate;
	private Date lastStatusChangeDate;
	private TaskStatus status = TaskStatus.Active;
	private TaskPriority priority = TaskPriority.Normal;
	private String comment = "";
	//private TasksDataSource dataSource;

	/**
	 * @param title
	 * @param status
	 * @param priority
	 * @param comment
	 */
	public Task(TasksDataSource dataSource, String title, TaskStatus status, TaskPriority priority, String comment) {
		super();
		//this.dataSource = dataSource;
		this.title = title;
		this.status = status;
		this.priority = priority;
		this.comment = comment;
		this.createdOnDate = new Date();
	}
	
	public Task(TasksDataSource dataSource) {
		this(dataSource,"",TaskStatus.Active,TaskPriority.Normal,"");
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
		this.lastStatusChangeDate = new Date();
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedOnDate() {
		return createdOnDate;
	}

	public Date getLastStatusChangeDate() {
		return lastStatusChangeDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}

	public void setLastStatusChangeDate(Date lastStatusChangeDate) {
		this.lastStatusChangeDate = lastStatusChangeDate;
	}
}