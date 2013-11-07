package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Task.Task;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

	private List<Task> tasks;
	private int layoutResourceId;
	private Context context;

	public TaskAdapter(Context context, int layoutResourceId, List<Task> tasks) {
		super(context, layoutResourceId, tasks);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.tasks = tasks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TasktHolder taskHolder = new TasktHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		taskHolder.task = tasks.get(position);
		taskHolder.tvTaskText = (TextView) row.findViewById(R.id.tvTaskName);
		taskHolder.btTaskState = (Button) row
				.findViewById(R.id.btChangeTaskState);
		taskHolder.btTaskState.setTag(taskHolder.task);

		taskHolder.tvTaskText.setText(taskHolder.task.getName());

		switch (taskHolder.task.getTaskState()) {
		case Empty:
			break;
		case Done:
			taskHolder.btTaskState
					.setBackgroundResource(android.R.drawable.checkbox_on_background);
			break;
		case UnDone:
			taskHolder.btTaskState
					.setBackgroundResource(android.R.drawable.ic_delete);
			break;
		}

		row.setTag(taskHolder);
		return row;
	}

	public class TasktHolder {
		Task task;
		TextView tvTaskText;
		Button btTaskState;
	}
}