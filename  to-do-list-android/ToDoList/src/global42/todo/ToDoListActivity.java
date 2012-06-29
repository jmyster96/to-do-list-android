package global42.todo;

import global42.todo.persistence.ListSorting;
import global42.todo.persistence.TasksDataSource;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
				onButtonClick();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.updateList();
	}

	public void onButtonClick() {
		if (titleTextField.getText().length() == 0) {
			Task task = new Task();
			switchToEdit(task);
		} else {
			Task newTask = new Task(titleTextField.getText().toString());
			dataSource.insert(newTask);
			titleTextField.setText("");
			updateList();
		}
	}
	public void onItemClick(Task clickedTask){
		this.switchToEdit(clickedTask);
	}

	private void switchToEdit(Task task) {
		Intent intent = new Intent(this, TaskEditActivity.class);
		intent.putExtra("taskObject", task);
		startActivityForResult(intent, 1);
	}

	private void updateList() {
		//TODO get the ListSorting from the saved user-data
		Collection<Task> tasks = dataSource.getTasksSorted(ListSorting.Priority);
		taskList.removeAllViewsInLayout();
		for (Task task : tasks) {
			TaskItemView taskItem = new TaskItemView(getApplicationContext(), task, this);
			taskList.addView(taskItem);
		}
		taskList.refreshDrawableState();
	}
}