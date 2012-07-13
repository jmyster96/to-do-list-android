package global42.todo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Gizzi
 * model to represent a particular task in the list
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

	public Task(String title, TaskStatus status, TaskPriority priority, String comment) {
		super();
		this.setId(-1);
		this.setTitle(title);
		this.setStatus(status);
		this.setPriority(priority);
		this.setComment(comment);
		this.setCreatedOnDate(new Date());
	}

	public Task(String title) {
		this(title, TaskStatus.Active, TaskPriority.Normal, new String());
	}

	public Task() {
		this(new String());
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
