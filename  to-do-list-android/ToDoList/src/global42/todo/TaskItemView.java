package global42.todo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
			this.priority.setImageResource(R.drawable.ic_low);
		}

		else if (tp.priority == 2) {
			this.priority.setImageResource(R.drawable.ic_normal);
		}

		else if (tp.priority == 3) {
			this.priority.setImageResource(R.drawable.ic_high);
		}

		this.title = new TextView(context);
		this.title.setMinimumWidth(310);
		this.title.setGravity(Gravity.CENTER);
		this.title.setText(this.task.getTitle());

		this.checkButton = new ImageButton(context);
		this.checkButton.setImageResource(R.drawable.black);
		this.checkButton.setMinimumHeight(84);
		this.checkButton.setMinimumWidth(84);
		this.checkButton.setMaxHeight(84);
		this.checkButton.setMaxWidth(84);
		this.checkButton.setClickable(true);
		this.checkButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) 
			{
				for(int i=0;i<10000;i++)
				{
					if(i%2==0)
					{checkButton.setImageResource(R.drawable.done);					}
					if(i%2!=0)
					{checkButton.setImageResource(R.drawable.black);}
				}}}
			
);

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

	public Task getTask() {
		return this.task;
	}
}