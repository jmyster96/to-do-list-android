package global42.todo;

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
		// TODO Auto-generated method stub
		super.onResume();
		this.updateList();
	}

	public void onButtonClick() {
		if (titleTextField.getText().length() == 0) {
			Intent intent = new Intent(this, TaskEditActivity.class);
			Task task = new Task();
			//task.setTitle("Penis");
			//task.setPriority(TaskPriority.Low);
			//task.setComment("Hallo");
			intent.putExtra("taskObject", task);
			startActivityForResult(intent, 1);
		} else {
			Task newTask = new Task(titleTextField.getText().toString());
			dataSource.insert(newTask);
//			TaskListController.getInstance().addTask(
//					titleTextField.getText().toString());
			titleTextField.setText("");
			updateList();
		}
	}

	private void updateList() {
		Collection<Task> tasks = dataSource.getAllTasks();//TaskListController.getInstance().getTasks();
		taskList.removeAllViewsInLayout();
		for (Task task : tasks) {
//			Button taskItem = new Button(getApplicationContext());
//			taskItem.setText(task.getTitle());
			View taskItem = new TaskItemView(getApplicationContext(),task);
			taskList.addView(taskItem);
		}
		taskList.refreshDrawableState();
	}
}