package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class UserRemarksActivity extends BaseActivity {
		
	private CheckBox chbConnect;
	private CheckBox chbMedchanges;
	private CheckBox chbPflege;
	private EditText etOther;
	
	private UserRemark userRemark;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_notes);
			reloadData();
			initElements();
			setWriteAble();
			fillUp();
		}
		catch(Exception e){
			e.printStackTrace();
			criticalClose();
		}
	}
	
	private void initElements(){		
		chbConnect = (CheckBox)findViewById(R.id.chbConnect);
		chbMedchanges = (CheckBox)findViewById(R.id.chbMedchanges);
		chbPflege = (CheckBox)findViewById(R.id.chbPflege);
		etOther = (EditText)findViewById(R.id.etOther);
		chbConnect.setChecked((userRemark.getCheckboxes() & 1) == 1);
		chbMedchanges.setChecked((userRemark.getCheckboxes() & 2) == 2);
		chbPflege.setChecked((userRemark.getCheckboxes() & 4) == 4);
	}
	
	private void reloadData() {
		Employment employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		userRemark = UserRemarkManager.Instance().load(Option.Instance().getEmploymentID());
		if (userRemark == null)
			userRemark = new UserRemark(Option.Instance().getEmploymentID(), Option.Instance().getWorkerID(), employment.getPatientID(), false, false, false, false, "");
	}
	
	private void fillUp() {
		etOther.setText(userRemark.getName());
	}
	
	private void pickUp(){
		userRemark.setID(Option.Instance().getEmploymentID());
		userRemark.setName(etOther.getText().toString());
		userRemark.setCheckboxes(chbConnect.isChecked()
				, chbMedchanges.isChecked()
				, chbPflege.isChecked()
				, etOther.getText().toString() != "");
		userRemark.setDate(new Date());
	}
	
	private void save(){
		UserRemarkManager.Instance().save(userRemark);		
	}
	
	public void btUserRemarkSaveClick(View view){
		pickUp();
		save();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if(bundle == null)
			return;
		setResult(UserRemarksActivity.RESULT_OK, intent);
		finish();
		
	}
	
	@Override
	public void onBackPressed() {
		setResult(UserRemarksActivity.RESULT_CANCELED);
		finish();
	}
	
	private void setWriteAble(){
		Intent intentSave = getIntent();
		Bundle bundle = intentSave.getExtras();
		Boolean viewMode = false;
		if(bundle != null){
			viewMode = bundle.getBoolean("ViewMode");
			CheckBox chbConnect = (CheckBox)findViewById(R.id.chbConnect);
			CheckBox chbMedchanges = (CheckBox)findViewById(R.id.chbMedchanges);
			CheckBox chbPflege = (CheckBox)findViewById(R.id.chbPflege);
			TextView tvOther = (TextView)findViewById(R.id.tvOther2);
			EditText etOther = (EditText)findViewById(R.id.etOther);
			Button btUserRemarkSave = (Button)findViewById(R.id.btUserRemarkSave);
			try {
				chbConnect.setClickable(!viewMode);
				chbMedchanges.setClickable(!viewMode);
				chbPflege.setClickable(!viewMode);
				tvOther.setClickable(!viewMode);
				etOther.setFocusable(!viewMode);
				btUserRemarkSave.setEnabled(!viewMode);
			} catch(Exception er) {
				er.printStackTrace();
			}
		}
	}
}


