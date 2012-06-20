/**
 * 
 */
package global42.todo;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
	
	public TaskItemView(Context context, Task task) {
		super(context);
		this.task = task;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.setBackgroundColor(Color.WHITE);
		this.priority = new ImageView(context);
		this.priority.setImageResource(R.drawable.ic_priority);
		
		this.title = new TextView(context);
		this.title.setGravity(Gravity.CENTER);
		this.title.setText(this.task.getTitle());
		
		this.checkButton = new ImageButton(context);
		this.checkButton.setImageResource(R.drawable.ic_done);
		
		this.addView(priority);
		this.addView(title);
		this.addView(checkButton);
	}

	
}
