package global42.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

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
			//currentTask = (Task)extras.getSerializable("taskObject");
			
			//String test = extras.getString("taskObject");
		}
	}

	private void onButtonSaveClick() {
//		currentTask.setTitle(editTitle.toString());
//		currentTask.setComment(editComment.toString());
//		currentTask.setPriority(TaskPriority.getTaskPriorityFor((int) ratingBarPriority.getRating()));
		
		//currentTask.save();
		
		Intent intent = new Intent(this, ToDoListActivity.class);
		startActivity(intent);
	}
}
