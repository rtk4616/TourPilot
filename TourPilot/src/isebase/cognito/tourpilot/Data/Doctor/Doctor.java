package isebase.cognito.tourpilot.Data.Doctor;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.IAddressable;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Doctor extends BaseObject implements IAddressable {

	public static final String SurnameField = "surname";
	public static final String AddressIDField = "address_id";
	
	public Address address;
	private int addressID;

	@MapField(DatabaseField = AddressIDField)
	public int getAddressID() {
		return addressID;
	}

	@MapField(DatabaseField = AddressIDField)
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	private String surname;
	
	@MapField(DatabaseField = SurnameField)
	public String getSurname() {
		return surname;
	}
	
	@MapField(DatabaseField = SurnameField)
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Doctor(){
		clear();
	}
	
	public Doctor(String initString) {
		address = new Address();
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setID(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		address.setStreet(parsingString.next(";"));
		address.setZip(parsingString.next(";"));
		address.setCity(parsingString.next(";"));
		address.setPhone(parsingString.next(";"));
		address.setPrivatePhone(parsingString.next(";"));
		address.setMobilePhone(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}
	
	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}
	
	@Override
	public String toString(){
		return String.format("%s\n%s\n%s,%s\n",getFullName(),address.getStreet(),address.getZip(),address.getCity());
	}

	@Override
	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.DOCTOR + ";");
		strValue += ncryptor.LToNcode(getID()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
	
	@Override
	protected void clear() {
		super.clear();
		address = new Address();
		setAddressID(EMPTY_ID);
		setSurname("");
	}

	@Override
	public Address getAddress() {
		// TODO Auto-generated method stub
		return address;
	}
	
}
