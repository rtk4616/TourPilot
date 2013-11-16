package isebase.cognito.tourpilot.Data.Diagnose;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class DiagnoseManager extends BaseObjectManager<Diagnose>{
	
	public static final String TableName = "Diagnoses";
	
	private static DiagnoseManager instance;

	public static DiagnoseManager Instance() {
		if (instance != null)
			return instance;
		instance = new DiagnoseManager();
		instance.open();
		return instance;
	}
	
	public DiagnoseManager() {
		super(Diagnose.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {		
		
	}

}
