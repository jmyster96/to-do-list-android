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

public class TodoAppWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";
	TasksDataSource tasksDataSource;
	List<Task> tasks;
	Iterator<Task> taskIterator;
	WidgetTimer timer;
	Context context;
	AppWidgetManager appWidgetManager;
	int[] appWidgetIds;
	
	public TodoAppWidgetProvider() {
		Timer timer = new Timer("Widget_Refresh_Thread");
		timer.scheduleAtFixedRate(new WidgetTimer(this), 5 * 1000, 5 * 1000);
	}

	private class WidgetTimer extends TimerTask {
		TodoAppWidgetProvider provider;

		public WidgetTimer(TodoAppWidgetProvider provider) {
			this.provider = provider;
		}

		@Override
		public void run() {
			System.out.println("run");
			this.provider.showNextTask();
			
		}

	}


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		System.out.println("onUpdate");
		// Get all ids
		this.appWidgetManager = appWidgetManager;
		this.appWidgetIds = appWidgetIds;
		this.context = context;
		getTasks();
		
		showNextTask();
		

	}

	private void getTasks() {
		TasksDataSource tasksDataSource = new TasksDataSource(this.context);
		List<Task> tasks = tasksDataSource.getTasksSorted(ListSorting.DateOfCreation, false);
		taskIterator = tasks.iterator();
	}

	public void showNextTask() {		
		System.out.println("showNextTask");
		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				TodoAppWidgetProvider.class);
		System.out.println("1 Zeile");
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		System.out.println("2 Zeile");
		for (int widgetId : allWidgetIds) {
			System.out.println("in der For Schleife");

			// Create some random data
			
			if(!taskIterator.hasNext()){
				getTasks();
//				taskIterator = tasks.iterator();
//				System.out.println("itereator wird hneu angelegt!");
			}
			System.out.println("itereator wird nicht neu angelegt!");
			Task newTask = taskIterator.next();
		
			
					
			
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),	R.layout.widget_layout);
			System.out.println("nach RemoteView");
			// Set the text
			remoteViews.setTextViewText(R.id.update, newTask.getTitle());

			// Register an onClickListener
			Intent intent = new Intent(context, ToDoListActivity.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);


			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

			appWidgetManager.updateAppWidget(widgetId, remoteViews);	
		
		}
	}
}