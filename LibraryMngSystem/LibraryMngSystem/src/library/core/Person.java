package library.core;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3914471730645968665L;

	protected String firstName;
	protected String lastName;
	protected String phoneNumber;
	protected Address address;

	public Person(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = new Address("","","","");
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	// Newly added
	public Address getAddressObj() {
		return address;
	}

	public String getAddress() {
		return address.getAddress();
	}

	public String toString() {
		return (firstName+" "+lastName+" \n"+address+"\n"+phoneNumber);
	}

}
