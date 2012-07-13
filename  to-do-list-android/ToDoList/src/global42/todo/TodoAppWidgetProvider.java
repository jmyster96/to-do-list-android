package global42.todo;

import global42.todo.persistence.ListSorting;
import global42.todo.persistence.TasksDataSource;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * @author Dominik
 * provides the widget
 */
public class TodoAppWidgetProvider extends AppWidgetProvider {
	private static final int SHOW_NEXT_TASK_TIMER = 5000;
	private Iterator<Task> taskIterator;
	private Context context;
	private AppWidgetManager appWidgetManager;
	int[] appWidgetIds;

	/**
	 * constructor used to init the showNextTaskTimer
	 */
	public TodoAppWidgetProvider() {
		Timer timer = new Timer("Widget_Refresh_Thread");
		timer.scheduleAtFixedRate(new WidgetTimer(this), SHOW_NEXT_TASK_TIMER, SHOW_NEXT_TASK_TIMER);
	}

	/**
	 * @author Dominik
	 * internal timer class to refresh the currently shown task in a particular interval 
	 */
	private class WidgetTimer extends TimerTask {
		TodoAppWidgetProvider provider;

		public WidgetTimer(TodoAppWidgetProvider provider) {
			this.provider = provider;
		}

		@Override
		public void run() {
			this.provider.showNextTask();

		}

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		this.appWidgetManager = appWidgetManager;
		this.appWidgetIds = appWidgetIds;
		this.context = context;
		getTasks();

		showNextTask();

	}

	private void getTasks() {
		TasksDataSource tasksDataSource = new TasksDataSource(this.context);
		List<Task> tasks = tasksDataSource.getTasksSorted(
				ListSorting.DateOfCreation, false);
		taskIterator = tasks.iterator();
	}

	/**
	 * show the next {@link Task} of the task list
	 * if the last item is reached, the list will be reloaded
	 */
	public void showNextTask() {
		ComponentName thisWidget = new ComponentName(context, TodoAppWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			if (!taskIterator.hasNext()) {
				getTasks();
			}
			
			Task newTask = taskIterator.next();
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			remoteViews.setTextViewText(R.id.update, newTask.getTitle());

			Intent intent = new Intent(context, ToDoListActivity.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
					intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}