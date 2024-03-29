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
 * extension of the linear layout to represent a row in the list of {@link Task}s
 */
public class TaskItemView extends LinearLayout {
	private Task task;
	private ImageView priority;
	private TextView title;
	private ImageButton checkButton;
	private ToDoListActivity callingActivity;

	/**
	 * constructor to create a new row in the list of {@link Task}s
	 * @param {@link Context} current context 
	 * @param {@link Task} task to be represented by this row
	 * @param {@link ToDoListActivity} activity
	 */
	public TaskItemView(Context context, Task task, ToDoListActivity activity) {
		super(context);
		this.setBackgroundResource(R.drawable.widget_background_transparent);
		this.task = task;
		this.callingActivity = activity;

		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		this.priority = new ImageView(context);
		this.priority.setMinimumHeight(84);
		this.priority.setMinimumWidth(84);
		this.priority.setMaxHeight(84);
		this.priority.setMaxWidth(84);
		TaskPriority tp;
		tp = task.getPriority();

		if (tp.priority == 1) {
			this.priority.setImageResource(R.drawable.ic_low_transparent_small);
		}

		else if (tp.priority == 2) {
			this.priority
					.setImageResource(R.drawable.ic_normal_transparent_small);
		}

		else if (tp.priority == 3) {
			this.priority
					.setImageResource(R.drawable.ic_high_transparent_small);
		}

		this.title = new TextView(context);
		this.title.setTextColor(Color.BLACK);
		this.title.setGravity(Gravity.CENTER_HORIZONTAL);
		this.title.setGravity(Gravity.CENTER_VERTICAL);
		this.title.setMinimumWidth(310);
		this.title.setGravity(Gravity.CENTER);
		this.title.setText(this.task.getTitle());

		this.checkButton = new ImageButton(context);
		if (this.task.getStatus() == TaskStatus.Done) {
			checkButton.setImageResource(R.drawable.done_transparent);
		}

		this.checkButton.setMinimumHeight(84);
		this.checkButton.setMinimumWidth(84);
		this.checkButton.setMaxHeight(84);
		this.checkButton.setMaxWidth(84);
		this.checkButton.setClickable(true);

		this.checkButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
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

	/**
	 * @return {@link Task} task represented by this row
	 */
	public Task getTask() {
		return this.task;
	}
}