package global42.todo;

import global42.todo.persistence.TasksDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * @author Dominik
 * Activity to edit or add a {@link Task}
 */
public class TaskEditActivity extends Activity {
	private Task currentTask = null;

	private EditText editTitle;
	private RatingBar ratingBarPriority;
	private EditText editComment;
	private Button buttonSave;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_edit);

		editTitle = (EditText) findViewById(R.id.editTaskEditTitle);
		ratingBarPriority = (RatingBar) findViewById(R.id.ratingBarEditTaskPriority);
		editComment = (EditText) findViewById(R.id.editTaskEditComment);

		buttonSave = (Button) findViewById(R.id.buttonEditTaskSave);
		buttonSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onButtonSaveClick();
			}
		});

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			currentTask = (Task) extras.getSerializable("taskObject");
			editTitle.setText(currentTask.getTitle());
			ratingBarPriority
					.setRating(currentTask.getPriority().getPriority());
			editComment.setText(currentTask.getComment());
		}
	}

	/**
	 * method called after user clicks the save button saves the entered data
	 * and saves the new {@link Task} or updates the edited one
	 */
	private void onButtonSaveClick() {
		currentTask.setTitle(editTitle.getText().toString());
		currentTask.setComment(editComment.getText().toString());
		currentTask.setPriority(TaskPriority
				.getTaskPriorityFor((int) ratingBarPriority.getRating()));

		TasksDataSource tasksDataSource = new TasksDataSource(this);
		tasksDataSource.save(currentTask);

		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}
}
