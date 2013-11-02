package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity {

	List<Patient> patients = new ArrayList<Patient>();
	List<Patient> donePatients = new ArrayList<Patient>();
	List<Patient> unDonePatients = new ArrayList<Patient>();
	
	public static Tour tour;
	public static Worker worker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients);
		reloadData();
		initPatients(patients.size());
		initListUndonePatients();
		initListDonePatients();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patients, menu);
		return true;
	}

	public void initListUndonePatients() {
		unDonePatients = new ArrayList<Patient>();

		final ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1, unDonePatients);
		final ListView lvListUndoneTasks = (ListView) findViewById(R.id.lvUndonePatients);

		lvListUndoneTasks.setAdapter(adapter);
		lvListUndoneTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
					}
				});

	}

	public void initListDonePatients() {
		donePatients = new ArrayList<Patient>();
		final ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1, donePatients);
		final ExpandableListView elvListDoneTasks = (ExpandableListView) findViewById(R.id.elvDonePatients);
		elvListDoneTasks.setAdapter(adapter);
	}

	private void initPatients(int tableSize) {

		if (tableSize > 0) {
			return;
		}
		PatientManager.Instance().add("Patient 1");
		PatientManager.Instance().add("Patient 2");
		PatientManager.Instance().add("Patient 3");
		PatientManager.Instance().add("Patient 4");
		PatientManager.Instance().add("Patient 5");
		PatientManager.Instance().add("Patient 6");
		PatientManager.Instance().add("Patient 7");
		PatientManager.Instance().add("Patient 8");
	}

	public void reloadData() {
		patients = PatientManager.Instance().load();
		for (Patient patient : patients) {
			if (patient.getIsDone())
				donePatients.add(patient);
			else
				unDonePatients.add(patient);
		}
	}
}