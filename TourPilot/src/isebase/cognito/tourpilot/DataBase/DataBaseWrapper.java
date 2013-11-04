package isebase.cognito.tourpilot.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

	//Table names
	public static final String WORKERS = "Workers";
	public static final String TOURS = "Tours";
	public static final String PATIENTS = "Patients";
	public static final String OPTIONS = "Options";
	
	//Base fields
	public static final String ID = "_id";
	public static final String NAME = "name";
	public static final String CHECKSUM = "checksum";
	
	//Patients fields
	public static final String ADDRESS = "address";
	public static final String IS_DONE = "is_done";
	
	//Settings fields
	public static final String WORKER_ID = "worker_id";
	public static final String TOUR_ID = "tour_id";
	public static final String EMPLOYMENT_ID = "employment_id";
	public static final String SERVER_IP = "server_ip";
	public static final String SERVER_PORT = "server_port";
	
	private static final String DATABASE_NAME = "TourPilot.db";
	private static final int DATABASE_VERSION = 1;

	private static final String WORKERS_TABLE_CREATE = "CREATE TABLE "
			+ WORKERS + "(" 
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " TEXT NOT NULL, "
			+ CHECKSUM + " INTEGER "
			+ ");";

	private static final String TOURS_TABLE_CREATE = "CREATE TABLE " 
			+ TOURS	+ "(" 
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ NAME + " TEXT NOT NULL, "
			+ CHECKSUM + " INTEGER "
			+ ");";

	private static final String PATIENTS_TABLE_CREATE = "CREATE TABLE "
			+ PATIENTS + "(" 
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " TEXT NOT NULL, "
			+ CHECKSUM + " INTEGER, "
			+ ADDRESS + " TEXT, "
			+ IS_DONE + " INTEGER NOT NULL DEFAULT 0 "
			+ ");";
	
	private static final String OPTIONS_TABLE_CREATE = "CREATE TABLE "
			+ OPTIONS + "(" 
			+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " TEXT, "
			+ CHECKSUM + " INTEGER, "
			+ WORKER_ID + " INTEGER, "
			+ TOUR_ID + " INTEGER, "
			+ EMPLOYMENT_ID + " INTEGER, "
			+ SERVER_IP + " TEXT, "
			+ SERVER_PORT + " INTEGER "
			+ ");";

	public DataBaseWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(WORKERS_TABLE_CREATE);
		db.execSQL(TOURS_TABLE_CREATE);
		db.execSQL(PATIENTS_TABLE_CREATE);
		db.execSQL(OPTIONS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + WORKERS);
		// db.execSQL("DROP TABLE IF EXISTS " + TOURS);
		// onCreate(db);
	}

}
