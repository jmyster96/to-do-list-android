package global42.todo;

import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToDoListActivity extends Activity {
    private TextView editText;
	private Button button;
	private LinearLayout linearLayout;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button = (Button) findViewById(R.id.addTaskButton);
        editText = (EditText) findViewById(R.id.newTaskTitle);
        linearLayout = (LinearLayout) findViewById(R.id.taskList);
        
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onButtonClick();
			}
		});
    }
    
    public void onButtonClick(){
    	TaskListController.getInstance().addTask(editText.getText().toString());
    	editText.setText("");
    	updateList();
    }

	private void updateList() {
		Collection<Task> tasks = TaskListController.getInstance().getTasks();
		linearLayout.removeAllViewsInLayout();
		for (Task task : tasks) {
			Button taskItem = new Button(getApplicationContext());
			taskItem.setText(task.getTitle());
			linearLayout.addView(taskItem);
		}
		linearLayout.refreshDrawableState();
	}
}