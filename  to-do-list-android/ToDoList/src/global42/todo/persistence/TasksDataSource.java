package global42.todo.persistence;

import global42.todo.Task;
import global42.todo.TaskPriority;
import global42.todo.TaskStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksDataSource implements Serializable {
	private static final long serialVersionUID = 1L;
	private SQLiteDatabase database;
	private SQLiteOpenHelper dbBuilder;
	
	// Database fields
	private String[] allColumns = { TaskTable.COLUMN_ID,
			TaskTable.COLUMN_TITLE,
			TaskTable.COLUMN_CREATED_ON_DATE,
			TaskTable.COLUMN_LAST_STATUS_CHANGE_DATE,
			TaskTable.COLUMN_STATUS,
			TaskTable.COLUMN_PRIORITY,
			TaskTable.COLUMN_COMMENT};

	public TasksDataSource(Context context) {
		dbBuilder = new ToDoDatabaseHelper(context);
		this.open();
	}

	public void open() throws SQLException {
		database = dbBuilder.getWritableDatabase();
	}

	public void close() {
		dbBuilder.close();
	}

	public Task insert(Task task) {
		ContentValues values = new ContentValues();
		values.put(TaskTable.COLUMN_TITLE, task.getTitle());
		values.put(TaskTable.COLUMN_CREATED_ON_DATE, task.getCreatedOnDate().getTime());
		values.put(TaskTable.COLUMN_LAST_STATUS_CHANGE_DATE, task.getLastStatusChangeDate().getTime());
		values.put(TaskTable.COLUMN_STATUS, task.getStatus().getStatusId());
		values.put(TaskTable.COLUMN_PRIORITY, task.getPriority().getPriority());
		values.put(TaskTable.COLUMN_COMMENT, task.getComment());
		
		long insertId = task.getId();
		if (insertId< 0) {
			insertId = database.insert(TaskTable.TABLE_NAME, null, values);
			
		} else {
			database.update(TaskTable.TABLE_NAME, values, TaskTable.COLUMN_ID + " = "+ task.getId(), null);
		}
		Cursor cursor = database.query(TaskTable.TABLE_NAME,
				allColumns, TaskTable.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Task newTask = readCursor(cursor);
		cursor.close();
		return newTask;
	}

	public void delete(Task task) {
		long id = task.getId();
		database.delete(TaskTable.TABLE_NAME, TaskTable.COLUMN_ID + " = " + id, null);
	}

	public List<Task> getAllTasks() {
		return readCursorList(database.query(TaskTable.TABLE_NAME, allColumns, null, null, null, null, null));
	}
	
	public List<Task> getTasksSorted(ListSorting sorting) {
		return getTasksSorted(sorting, true);
	}
	
	public List<Task> getTasksSorted(ListSorting sorting, boolean showDoneItems) {
		String whereClause = null;
		if(!showDoneItems)
			whereClause = new String("" + TaskTable.COLUMN_STATUS + " = '1'");
		
		Cursor returnedCursor;
		switch (sorting) {
			case Priority:
				returnedCursor = database.query(TaskTable.TABLE_NAME, allColumns, whereClause, null, null, null, TaskTable.COLUMN_PRIORITY + " DESC");				
				break;
//			case DueDate:
//				database.query(TaskTable.TABLE_NAME, allColumns, whereClause, null, null, null, TaskTable.COLUMN_DUEDATE + " ASC");
//				break;
			case DateOfCreation:
			default:
				returnedCursor = database.query(TaskTable.TABLE_NAME, allColumns, whereClause, null, null, null, TaskTable.COLUMN_CREATED_ON_DATE + " DESC");
				break;
		}
		return readCursorList(returnedCursor);
	}

	private List<Task> readCursorList(Cursor cursor) {
		List<Task> tasks = new ArrayList<Task>();
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task comment = readCursor(cursor);
			tasks.add(comment);
			cursor.moveToNext();
		}

		cursor.close();
		
		return tasks;
	}

	private Task readCursor(Cursor cursor) {
		Task task = new Task();
		task.setId(cursor.getLong(0));
		task.setTitle(cursor.getString(1));
		task.setCreatedOnDate(new Date(cursor.getLong(2)));
		task.setLastStatusChangeDate(new Date(cursor.getLong(3)));
		task.setStatus(TaskStatus.getTaskStatusFor(cursor.getInt(4)));
		task.setPriority(TaskPriority.getTaskPriorityFor(cursor.getInt(5)));
		task.setComment(cursor.getString(6));
		return task;
	}

	public void save(Task task) {
		insert(task);
	}
}