package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class PinDialog {

	public EditText etPin;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
//		etPin = new EditText(StaticResources.getBaseContext());
//		etPin.setTextColor(Color.BLACK);
//		etPin.setHint(isebase.cognito.tourpilot.R.string.enter_pin);
//		etPin.setInputType(InputType.TYPE_CLASS_NUMBER
//				| InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//		adb.setView(etPin);
//		adb.setIcon(isebase.cognito.tourpilot.R.drawable.ic_action_screen_locked_to_landscape);
//		adb.setTitle(isebase.cognito.tourpilot.R.string.some_text);
//		adb.setMessage(isebase.cognito.tourpilot.R.string.pin_code);
//		adb.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//					}
//				});
//		adb.setNegativeButton(isebase.cognito.tourpilot.R.string.cancel,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//					}
//				});
		return null;//adb.create();
	}

}
