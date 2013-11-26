package isebase.cognito.tourpilot.Templates;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Address.IAddressable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddressAdapter<T extends IAddressable> extends ArrayAdapter<T> {

	private List<T> listAddress;
	private int layoutResourceId;
	private Context context;

	private View row;
	
	public AddressAdapter(Context context, int layoutResourceId, List<T> listAddress){
		super(context, layoutResourceId, listAddress);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.listAddress = listAddress;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		row = convertView;

		AddressHolder addressHolder = new AddressHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);

		addressHolder.address = listAddress.get(position);
		
		addressHolder.tvFullname = (TextView) row.findViewById(R.id.tvFullName);
		addressHolder.tvFullname.setText(addressHolder.address.getFullName());
		
		addressHolder.tvAddressName = (TextView) row.findViewById(R.id.tvAddressName);		
		addressHolder.tvAddressName.setText(addressHolder.address.getAddress().getAddressData());

		
		TableLayout tablePhones = (TableLayout)row.findViewById(R.id.tablePhones);
		if(addressHolder.address.getAddress().getRealPhone().length() > 0){
			
			TableRow rowPhone = showPhone(R.string.phone,addressHolder.address.getAddress().getPhone(),addressHolder.address.getAddress().getRealPhone(), tablePhones);
			tablePhones.addView(rowPhone);
		}
		if(addressHolder.address.getAddress().getRealPrivatePhone().length() > 0){
			
			TableRow rowPrivatePhone = showPhone(R.string.phone_private,addressHolder.address.getAddress().getPrivatePhone(),addressHolder.address.getAddress().getRealPrivatePhone(), tablePhones);
			tablePhones.addView(rowPrivatePhone);
		}
		if(addressHolder.address.getAddress().getRealMobilePhone().length() > 0){
			
			TableRow rowMobilePhone = showPhone(R.string.phone_mobile,addressHolder.address.getAddress().getMobilePhone(),addressHolder.address.getAddress().getRealMobilePhone(), tablePhones);
			tablePhones.addView(rowMobilePhone);
		}
		
		return row;
	}
	
	private TableRow showPhone(int PhoneLabel,String strPhoneNumber,String strRealphoneNumber,TableLayout tablePhones){
		
		OnClickListener imageClickListener;
		imageClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				onCallPhone(v);           
			}
		};
		int textSizeMiddle = 20;
		
		TableRow rowPhone = new TableRow(tablePhones.getContext());
		LinearLayout llPhone = new LinearLayout(rowPhone.getContext());
		TextView tvLabelPhone = new TextView(llPhone.getContext());
		TextView tvPhone = new TextView(llPhone.getContext());
		ImageButton ibCall = new ImageButton(rowPhone.getContext());
		
		tvLabelPhone.setText(PhoneLabel);
		tvLabelPhone.setTextSize(textSizeMiddle);
		
		tvPhone.setText(strPhoneNumber);
		tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSizeMiddle);
		tvPhone.setTypeface(Typeface.MONOSPACE, Typeface.BOLD|Typeface.ITALIC);
		tvPhone.setGravity(Gravity.RIGHT|Gravity.CENTER_HORIZONTAL|Gravity.FILL);
		tvPhone.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		llPhone.setOrientation(LinearLayout.VERTICAL);
		llPhone.setWeightSum(1);
		TableRow.LayoutParams paramsLL = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.5f);
		paramsLL.gravity = Gravity.LEFT|Gravity.CENTER_VERTICAL;
		llPhone.setLayoutParams(paramsLL);

		llPhone.addView(tvLabelPhone);
		llPhone.addView(tvPhone);

		TableRow.LayoutParams paramsButton = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,0.5f);
		paramsButton.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
		ibCall.setLayoutParams(paramsButton);
		ibCall.setImageResource(android.R.drawable.stat_sys_phone_call);
		ibCall.setClickable(true);
		ibCall.setId(PhoneLabel);
		ibCall.setOnClickListener(imageClickListener);
		ibCall.setTag(strRealphoneNumber);

		rowPhone.addView(llPhone);
		rowPhone.addView(ibCall);

		return rowPhone; 
	}
	
	public class AddressHolder{

		IAddressable address;

		TextView tvFullname;
		
		ImageButton imageCallPhone;
		ImageButton imageCallPrivatePhone;
		ImageButton imageCallMobilePhone;

		TextView tvPhone;
		TextView tvPrivatePhone;
		TextView tvMobilePhone;
		
		TextView tvAddressName;
	}
	
	public void onCallPhone(View view) {		
		String realPhone = (String) view.getTag();
		try{
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + realPhone));
			getContext().startActivity(callIntent);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}

