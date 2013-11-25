package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.DataInterfaces.Job.IJob;
import isebase.cognito.tourpilot.DataInterfaces.Job.JobComparer;
import isebase.cognito.tourpilot.Templates.WorkEmploymentAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity {

	private List<Employment> employments;
	private List<Work> works;
	private List<IJob> items;
	private Work work;
	private DialogFragment patientsDialog;
	private String[] patientsArr;
	
	private Button btTourEnd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_patients);
			init();
			reloadData();
			fillUpTitle();
			fillUp();
			initDialogs();

		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patients_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_additional_work:
			startAdditionalWorksActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
		startToursActivity();
	}
	
	public void btEndTourClick(View view){
		Option.Instance().setPilotTourID(BaseObject.EMPTY_ID);
		Option.Instance().save();
		startSyncActivity();
	}

	private void init(){
		btTourEnd = (Button) findViewById(R.id.btEndTour);
	}
	
	private void fillUp() {
		WorkEmploymentAdapter adapter = new WorkEmploymentAdapter(this,R.layout.row_work_employment_template, items);
		ListView lvEmployments = (ListView) findViewById(R.id.lvEmployments);
		lvEmployments.setAdapter(adapter);
		lvEmployments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (items.get(position) instanceof Employment)
				{
					saveSelectedEmploymentID(((Employment)items.get(position)).getID());
					startTasksActivity();
				}
				else
				{
					work = (Work) items.get(position);
					List<Patient> patients = PatientManager.Instance().loadByIDs(work.getPatientIDs());
					if (patients.size() > 0)
					{
						patientsArr = new String[patients.size()];
						int counter = 0;
						for (Patient patient : patients)
							patientsArr[counter++] = patient.getFullName(); 
					}
					else						
						patientsArr = new String[]{getString(R.string.no_any_patient)};
					patientsDialog.show(getSupportFragmentManager(), "patientsDialog");
				}
			}
		});
	}

	private void reloadData() {
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		works = WorkManager.Instance().loadAll(Work.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		items = new ArrayList<IJob>();
		items.addAll(employments);
		items.addAll(works);
		Collections.sort(items, new JobComparer());
		checkTourEndButton();
	}
	
	private void checkTourEndButton(){
		int taskCount = items.size();
		int doneTaskCount = 0;
		int syncTaskCount = 0;
		for(IJob job : items){
			if(job.isDone())
				doneTaskCount++;
			if(job.wasSent())
				syncTaskCount++;
		}
		btTourEnd.setEnabled(!(doneTaskCount < taskCount || syncTaskCount == taskCount));		
	}
	
	private void fillUpTitle() {
		PilotTour pt = PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID());
		Worker worker = Option.Instance().getWorker();
		setTitle(String.format("%1$s, %2$s - %3$s"
				, worker.getName()
				, pt.getName()
				, DateUtils.WeekDateFormat.format(pt.getPlanDate())));
	}
	
	private void saveSelectedEmploymentID(int emplID) {
		Option.Instance().setEmploymentID(emplID);
		Option.Instance().save();
	}
	
	private void initDialogs() {
		patientsDialog = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
						.setTitle(work.getName())
						.setItems(patientsArr, new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						})
						.setPositiveButton(
								isebase.cognito.tourpilot.R.string.ok, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										
									}
								});
				return adb.create();
			}
		};
	}
}
