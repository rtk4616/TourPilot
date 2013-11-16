 package isebase.cognito.tourpilot.Activity;
 
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
 
 public class SynchronizationActivity extends BaseActivity{
 	
	private ListView lvConnectionLog;
 	private SynchronizationHandler syncHandler;
	private ArrayAdapter<String> adapter;
	private ConnectionStatus connectionStatus;
	private ConnectionAsyncTask connectionTask;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	private ProgressBar progressBar;
	private TextView progressText;
 	
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_synchronization);
		lvConnectionLog = (ListView) findViewById(R.id.lvSyncText);
		progressBar = (ProgressBar) findViewById(R.id.pbSync);
		progressText = (TextView)findViewById(R.id.tvProgress);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		lvConnectionLog.setAdapter(adapter);
		adapter.add("Waiting open sockets to automatically close...");
 		syncHandler = new SynchronizationHandler() {
 			
 			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
				if(!text.equals("")){
					adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
					if(!isOK){
						progressText.setText(text);
					}
				}
				if(isOK){		
					Intent nextActivity = (Option.Instance().getWorkerID() == -1) 
							? new Intent(getApplicationContext(), WorkersActivity.class) // all workers
							: new Intent(getApplicationContext(), ToursActivity.class); // show tours for worker
					startActivity(nextActivity);					
				}
 			}
 			
 			@Override
 			public void onItemSynchronized(String text) {
				adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
				connectionStatus.nextState();
				connectionTask = new ConnectionAsyncTask(connectionStatus);
				connectionTask.execute(); 
 			}
 			
 			@Override
 			public void onProgressUpdate(String text, int progress){
 				progressBar.setProgress(progress);
 				progressText.setText(text);
 			}

			@Override
			public void onProgressUpdate(String text) {
				progressBar.setMax(connectionStatus.getTotalProgress());				
			}				
 		};	
	
		connectionStatus = new ConnectionStatus(syncHandler);
		connectionTask = new ConnectionAsyncTask(connectionStatus);
		connectionTask.execute();
 	}	
 	
 	@Override
 	public void onBackPressed() {
		connectionTask.terminate();
		super.onBackPressed();
 	}
}
