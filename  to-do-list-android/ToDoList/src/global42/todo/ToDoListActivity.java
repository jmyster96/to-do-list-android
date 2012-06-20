package global42.todo;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

	public void onButtonClick() {
		if (titleTextField.getText().length() == 0) {
			Intent intent = new Intent(this, TaskEditActivity.class);
			// intent.putExtra(STAU_ID, stauId);
			startActivity(intent);
		} else {
			TaskListController.getInstance().addTask(
					titleTextField.getText().toString());
			titleTextField.setText("");
			updateList();
		}
	}

	private void updateList() {
		Collection<Task> tasks = TaskListController.getInstance().getTasks();
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