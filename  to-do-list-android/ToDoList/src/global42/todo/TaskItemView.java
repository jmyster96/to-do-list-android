/**
 * 
 */
package global42.todo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Dominik
 *
 */
public class TaskItemView extends LinearLayout {
	private Task task;
	private ImageView priority;
	private TextView title;
	private ImageButton checkButton;
	private ToDoListActivity callingActivity;
	
	public TaskItemView(Context context, Task task, ToDoListActivity activity) {
		super(context);
		this.task = task;
		this.callingActivity = activity;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.setBackgroundColor(Color.WHITE);
		this.priority = new ImageView(context);
		this.priority.setImageResource(R.drawable.ic_priority);
		
		this.title = new TextView(context);
		this.title.setGravity(Gravity.CENTER);
		this.title.setText(this.task.getTitle() + "_" + this.task.getStatus());
		
		this.checkButton = new ImageButton(context);
		this.checkButton.setImageResource(R.drawable.ic_done);
		this.checkButton.setClickable(true);
		this.checkButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				callingActivity.onSetDoneClick(getTask());
			}
		});
		
		this.addView(priority);
		this.addView(title);
		this.addView(checkButton);
		
		this.setClickable(true);
		this.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				callingActivity.onItemClick(getTask());
			}
		});
	}

	public Task getTask(){
		return this.task;
	}
}
