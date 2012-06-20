package global42.todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDataSource {

	// Database fields
	private SQLiteDatabase database;
	private SQLiteDatabaseBuilder dbBuilder;
	private String[] allColumns = { SQLiteDatabaseBuilder.COLUMN_ID,
			SQLiteDatabaseBuilder.COLUMN_TITLE,
			SQLiteDatabaseBuilder.COLUMN_CREATED_ON_DATE,
			SQLiteDatabaseBuilder.COLUMN_LAST_STATUS_CHANGE_DATE,
			SQLiteDatabaseBuilder.COLUMN_STATUS,
			SQLiteDatabaseBuilder.COLUMN_PRIORITY,
			SQLiteDatabaseBuilder.COLUMN_COMMENT};

	public TasksDataSource(Context context) {
		dbBuilder = new SQLiteDatabaseBuilder(context);
	}

	public void open() throws SQLException {
		database = dbBuilder.getWritableDatabase();
	}

	public void close() {
		dbBuilder.close();
	}

	public Task insert(Task task) {
		ContentValues values = new ContentValues();
		values.put(SQLiteDatabaseBuilder.COLUMN_TITLE, task.getTitle());
		values.put(SQLiteDatabaseBuilder.COLUMN_CREATED_ON_DATE, task.getCreatedOnDate().getTime());
		values.put(SQLiteDatabaseBuilder.COLUMN_LAST_STATUS_CHANGE_DATE, task.getLastStatusChangeDate().getTime());
		values.put(SQLiteDatabaseBuilder.COLUMN_STATUS, task.getStatus().toString());
		values.put(SQLiteDatabaseBuilder.COLUMN_PRIORITY, task.getPriority().toString());
		values.put(SQLiteDatabaseBuilder.COLUMN_COMMENT, task.getComment());
		
		long insertId = database.insert(SQLiteDatabaseBuilder.TABLE_NAME, null, values);
		Cursor cursor = database.query(SQLiteDatabaseBuilder.TABLE_NAME,
				allColumns, SQLiteDatabaseBuilder.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Task newTask = readCursor(cursor);
		cursor.close();
		return newTask;
	}

	public void delete(Task task) {
		long id = task.getId();
		database.delete(SQLiteDatabaseBuilder.TABLE_NAME, SQLiteDatabaseBuilder.COLUMN_ID + " = " + id, null);
	}

	public List<Task> getAllTasks() {
		return readCursorList(database.query(SQLiteDatabaseBuilder.TABLE_NAME, allColumns, null, null, null, null, null));
	}
	
	public List<Task> getTasksSorted() {
		return readCursorList(database.query(SQLiteDatabaseBuilder.TABLE_NAME, allColumns, null, null, null, null, SQLiteDatabaseBuilder.COLUMN_CREATED_ON_DATE + " DESC"));
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
}