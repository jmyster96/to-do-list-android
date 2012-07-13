package global42.todo;

import global42.todo.persistence.ListSorting;
import global42.todo.persistence.TasksDataSource;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * @author Dominik
 * Activity of the main screen containing the {@link Task} list and the add button
 */
public class ToDoListActivity extends Activity {
	private EditText titleTextField;
	private Button addButton;
	private LinearLayout taskList;
	private TasksDataSource dataSource;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.dataSource = new TasksDataSource(getApplicationContext());

		this.setContentView(R.layout.main);

		addButton = (Button) findViewById(R.id.addTaskButton);
		titleTextField = (EditText) findViewById(R.id.newTaskTitle);
		taskList = (LinearLayout) findViewById(R.id.taskList);

		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onAddButtonClick();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.updateList();
	}

	/**
	 * method is called after the "Add" button is clicked
	 * quick add a task or intent activity to add a new task
	 */
	public void onAddButtonClick() {
		if (titleTextField.getText().length() == 0) {
			Task task = new Task();
			switchToEdit(task);
		} else {
			Task newTask = new Task(titleTextField.getText().toString());
			dataSource.save(newTask);
			titleTextField.setText("");
			updateList();
		}
	}

	/**
	 * method called after a user clicked on an item in the task list to edit the item
	 * @param {@link Task} task the user clicked on  
	 */
	public void onItemClick(Task clickedTask) {
		this.switchToEdit(clickedTask);
	}

	/**
	 * open the edit activity of a particular task
	 * @param {@link Task} task to be edited
	 */
	private void switchToEdit(Task task) {
		Intent intent = new Intent(this, TaskEditActivity.class);
		intent.putExtra("taskObject", task);
		startActivityForResult(intent, 1);
	}

	/**
	 * update the task list using the defined preferences (e. g. order) and using the database
	 */
	private void updateList() {
		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		int sortBy = myPrefs.getInt(
				SettingsActivity.selected_radio_button_setting,
				R.id.radio_CreationDate);
		
		boolean showDone = myPrefs.getBoolean(SettingsActivity.show_done_items_setting, true);

		Collection<Task> tasks =  dataSource
					.getTasksSorted(ListSorting.getListSortingFor(sortBy),showDone);
		

		
		taskList.removeAllViewsInLayout();
		for (Task task : tasks) {
			TaskItemView taskItem = new TaskItemView(
					getApplicationContext(), task, this);
			taskList.addView(taskItem);
		}
		taskList.refreshDrawableState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, 1);

		}
		return true;
	}

	/**
	 * method will be called after user clicked the "done" button
	 * @param {@link Task} task the user wants to set as "done"
	 */
	public void onSetDoneClick(Task task) {
		task.setStatus(TaskStatus.Done);
		dataSource.save(task);
		updateList();
	}
}