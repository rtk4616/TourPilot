package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WorkersActivity extends BaseActivity {

	List<Worker> workers = new ArrayList<Worker>();	
	
	public Dialog pinDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		reloadData();
		initTable(workers.size());
		initListWorkers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:
			return getPinDialog();
		default:
			return null;
		}
	}

	public void initListWorkers() {
		final ListView listView = (ListView) findViewById(R.id.lvWorkers);
		ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(this,
				android.R.layout.simple_list_item_1, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				showDialog(0);
				((TextView) pinDialog.findViewById(R.id.tvWorkerName)).
					setText(((Worker)listView.getItemAtPosition(position)).getName());
				((EditText) pinDialog.findViewById(R.id.evPin)).
					setText(StaticResources.stringEmpty);
			}

		});
	}
	
	private void initTable(int tableSize) {
		if (tableSize > 0)
			return;
		for (int i = 0; i < 10; i++)
			WorkerManager.Instance().add(new Worker("Worker " + i));
		reloadData();
	}	

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(), OptionsActivity.class);
		startActivity(optionsActivity);
	}
	
	public void reloadData() {
		workers = WorkerManager.Instance().load();
	}

	private Dialog getPinDialog() {
		if (pinDialog != null)
			return pinDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						String name = ((TextView) pinDialog
								.findViewById(R.id.tvWorkerName)).getText()
								.toString();
						String pinStr = ((EditText) pinDialog
								.findViewById(R.id.evPin)).getText().toString();
						if (!StaticResources.checkWorkerPIN(name, pinStr))
							return;
						Intent toursActivity = new Intent(
								getApplicationContext(), ToursActivity.class);
						startActivity(toursActivity);
					}
				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}
				});
		pinDialog = builder.create();
		return pinDialog;
	}


}
