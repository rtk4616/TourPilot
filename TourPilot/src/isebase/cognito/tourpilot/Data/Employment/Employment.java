package isebase.cognito.tourpilot.Data.Employment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;

public class Employment extends BaseObject {
	
	public static final String PatientIDField = "patient_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String TourIDField = "tour_id"; 
	public static final String DateField = "date";
	public static final String IsDoneField = "is_done";
	public static final String IsAbortedField = "is_aborted";
		
	private int patientID;	
	private int pilotTourID;
	private Date date;
	private int tourID;

	private boolean isDone;
	private boolean isAborted; 
		
	private List<Task> tasks =  new ArrayList<Task>();
	
	private Patient patient;
	private PilotTour pilotTour;

	@MapField(DatabaseField = IsDoneField)
	public boolean isDone() {
		return isDone;
	}

	@MapField(DatabaseField = IsDoneField)
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	@MapField(DatabaseField = IsAbortedField)
	public boolean isAborted() {
		return isAborted;
	}

	@MapField(DatabaseField = IsAbortedField)
	public void setAborted(boolean isAborted) {
		this.isAborted = isAborted;
	}
	
	@MapField(DatabaseField = DateField)
	public Date getDate() {
		return date;
	}

	@MapField(DatabaseField = DateField)
	public void setDate(Date date) {
		this.date = date;
	}
	
	@MapField(DatabaseField = TourIDField)
	public int getTourID() {
		return tourID;
	}

	@MapField(DatabaseField = TourIDField)
	public void setTourID(int id) {
		this.tourID = id;
	}
	
	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID() {
		return pilotTourID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int pilotTourID) {
		this.pilotTourID = pilotTourID;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public PilotTour getPilotTour() {
		return pilotTour;
	}

	public void setPilotTour(PilotTour pilotTour) {
		this.pilotTour = pilotTour;
	}
	
	public Employment() {
		
	}
	
	@Override
	public String toString() {
		return getName() + "  " + DateUtils.HourMinutesFormat.format(getDate());
	}

	public String getTime() {
		 return DateUtils.HourMinutesFormat.format(getDate());
	}
		
	@Override
	public String forServer() {
		// TODO Auto-generated method stub
		return "";
	}
	
}