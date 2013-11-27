package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.os.Bundle;
import android.widget.ListView;

public class AddressPatientActivity extends BaseActivity {

	private List<Patient> patients;
	private Employment employment;
	private AddressAdapter<Patient> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_address);
			reloadData();
			initForm();
			fillUpTitle();
		}
		catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void initForm(){
		adapter = new AddressAdapter<Patient>(this
				, R.layout.row_address_template, patients);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListPatientAdrress);
		doctorsListView.setAdapter(adapter);
	}
	
	public void reloadData() {
		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		patients = new ArrayList<Patient>();
		patients.add(PatientManager.Instance().loadAll(employment.getPatientID()));
	}
	
	private void fillUpTitle(){
		setTitle(getString(R.string.menu_address) + ". " + employment.getName());
	}
	
}
