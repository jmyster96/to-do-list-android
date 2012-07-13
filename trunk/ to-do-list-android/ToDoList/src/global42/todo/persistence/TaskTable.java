/**
 * 
 */
package global42.todo.persistence;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Ulf
 * represent of the table "tasks" in the database
 */
public class TaskTable {
	
	public static final String TABLE_NAME = "tasks";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_CREATED_ON_DATE = "created_on_date";
	public static final String COLUMN_LAST_STATUS_CHANGE_DATE = "last_status_change_date";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_PRIORITY = "priority";
	public static final String COLUMN_COMMENT = "comment";;
	
	private static final String TABLE_TASKS_CREATE_COMMAND = "create table "
		+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, " 
		+ COLUMN_TITLE + " varchar(255) not null, "
		+ COLUMN_CREATED_ON_DATE + " integer(64) not null, "
		+ COLUMN_LAST_STATUS_CHANGE_DATE + " integer(64) not null, "
		+ COLUMN_STATUS + " integer(2) not null, "
		+ COLUMN_PRIORITY + " integer(2) not null, "
		+ COLUMN_COMMENT + " text not null);";

	/**
	 * creates the table "tasks" in the given database
	 * @param {@link SQLiteDatabase} database which should be used
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_TASKS_CREATE_COMMAND);
	}

	/**
	 * deletes the old version of the table "tasks" and generates the new version
	 * all data will be removed
	 * @param {@link SQLiteDatabase} database which should be used
	 * @param {@link Integer} oldVersion from which should be updated
	 * @param {@link Integer} newVersion to which should be updated
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(ToDoDatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
}
