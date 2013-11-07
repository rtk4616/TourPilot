package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class TaskManager extends BaseObjectManager<Task> {

	private static TaskManager instance;

	public static TaskManager Instance() {
		if (instance != null)
			return instance;
		instance = new TaskManager();
		instance.open();
		return instance;
	}

	public TaskManager() {
		super(Task.class);
	}

	@Override
	public String getRecTableName() {
		return dbHelper.TASKS;
	}

}