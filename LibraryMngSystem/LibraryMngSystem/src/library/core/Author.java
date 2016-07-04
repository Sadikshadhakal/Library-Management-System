package library.core;

import java.io.Serializable;

public class Author extends Person implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8324523308854356332L;

	private String credentials;
	private String shortBio;
	private Integer Id;

	public Author(Integer id,String firstName, String lastName, String phoneNumber, String street, String city, String state,
			String zip, String credentials, String shortBio) {
		super(firstName, lastName, phoneNumber);
		Address newAddress = new Address(street, city, state, zip);
		super.setAddress(newAddress);
this.setId(id);
		this.credentials = credentials;
		this.shortBio = shortBio;
	}



	public Author(String firstName, String lastName, String phoneNumber) {
		super(firstName, lastName, phoneNumber);
	}



	public String getCredentials() {
		return credentials;
	}

	public String getShortBio() {
		return shortBio;
	}
	public String getName()
	{

		return (firstName + " " + lastName);

	}

	public String toString() {
		return (firstName + " " + lastName + " \n" + address + "\n" + phoneNumber + "\n" + credentials + "\n"
				+ shortBio);
	}



	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}

}
