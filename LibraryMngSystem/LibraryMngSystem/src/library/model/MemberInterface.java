package library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a User.
 *
 */
public class MemberInterface {

	private final IntegerProperty memberID;
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty phone;
	private final StringProperty street;
	private final StringProperty city;
	private final StringProperty state;
	private final StringProperty zip;
	private  StringProperty name;

	/**
	 * Default constructor.
	 */
	public MemberInterface() {
		this(0, null, null, null, null, null, null, null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 */
	public MemberInterface(int id, String firstName, String lastName, String phone, String street, String city,
			String state, String zip) {

		this.memberID = new SimpleIntegerProperty(id);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.street = new SimpleStringProperty(street);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleStringProperty(zip);
		this.name=new SimpleStringProperty(firstName+" "+lastName);
	}

	// Member ID
	public int getMemberID() {
		return memberID.get();
	}

	public void setMemberID(int memberID) {
		this.memberID.set(memberID);
	}

	public IntegerProperty userIDProperty() {
		return memberID;
	}

	// FirstName
	public String getfirstName() {
		return firstName.get();
	}

	public void setfirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}


	public StringProperty NameProperty() {

		return name;
	}
	public void setName(String nme)
	{
		this.name.set(nme);
	}
	// LastName
	public String getlastName() {
		return lastName.get();
	}

	public void setlastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	// Phone
	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	public StringProperty phoneProperty() {
		return phone;
	}

	// Street
	public String getStreet() {
		return street.get();
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public StringProperty streetProperty() {
		return street;
	}

	// City
	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public StringProperty cityProperty() {
		return city;
	}

	// State
	public String getState() {
		return state.get();
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public StringProperty stateProperty() {
		return state;
	}

	// Zip Code
	public String getZip() {
		return zip.get();
	}

	public void setZip(String zip) {
		this.zip.set(zip);
	}

	public StringProperty zipProperty() {
		return zip;
	}

}