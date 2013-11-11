package isebase.cognito.tourpilot.Data.Relative;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Relative extends BaseObject {

	public static final String SurnameField = "surname";
	public static final String ShipField = "ship";
	
	private String surname;
	private String ship;

	public Address address;
	
	public Relative(String initString) {
		address = new Address();
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setId(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		address.setStreet(parsingString.next(";"));
		address.setZip(parsingString.next(";"));
		address.setCity(parsingString.next(";"));
		address.setPhone(parsingString.next(";"));
		setShip(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@MapField(DatabaseField = SurnameField)
	public String getSurname() {
		return surname;
	}

	@MapField(DatabaseField = SurnameField)
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@MapField(DatabaseField = ShipField)
	public String getShip() {
		return ship;
	}

	@MapField(DatabaseField = ShipField)
	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.RELATIVE + ";");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
}
