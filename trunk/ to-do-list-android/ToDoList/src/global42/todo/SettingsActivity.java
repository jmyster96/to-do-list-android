package global42.todo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {

	public static final String show_done_items_setting = "SHOW_DONE_ITEMS";
	public static final String selected_radio_button_setting = "SELECTED_RADIO_BUTTON";
	private CheckBox show_done_items;
	private RadioGroup sort_by_group;
	private RadioButton radio_priority;
	private RadioButton radio_creation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.settings_view);

		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		boolean check = myPrefs.getBoolean(show_done_items_setting, true);

		show_done_items = (CheckBox) findViewById(R.id.checkBoxDoneItems);
		show_done_items.setChecked(check);
		show_done_items.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onShowDoneClick();
			}
		});
		
		sort_by_group = (RadioGroup) findViewById(R.id.SortByGroup);
		int checked_radio_button_id = myPrefs.getInt(selected_radio_button_setting, R.id.radio_CreationDate);
		RadioButton checkedButton = (RadioButton) findViewById(checked_radio_button_id);
		checkedButton.setChecked(true);	

		radio_priority = (RadioButton) findViewById(R.id.radio_Priority);
		radio_priority.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onGroupClick();
			}
		});
		
		radio_creation = (RadioButton) findViewById(R.id.radio_CreationDate);
		radio_creation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				onGroupClick();
			}
		});

	}

	public void onShowDoneClick() {

		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();

		prefsEditor.putBoolean(show_done_items_setting,
				show_done_items.isChecked());

		prefsEditor.commit();
	}

	public void onGroupClick() {
		int checkedButtonId = sort_by_group.getCheckedRadioButtonId();
		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs",
				MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putInt(selected_radio_button_setting, checkedButtonId);

		prefsEditor.commit();

	}

}
