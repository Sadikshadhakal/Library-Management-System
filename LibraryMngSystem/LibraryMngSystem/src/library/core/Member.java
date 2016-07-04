package library.core;

import java.io.Serializable;

import library.General;

public class Member extends Person implements Serializable, Comparable<Member> {

	/**
	 *
	 */
	private static final long serialVersionUID = -3340360740574640290L;

	private int memberid;
	private String name;

	public Member(int memberid, String firstName, String lastName, String phoneNumber, String street, String city, String state, String zip) {
		super(firstName, lastName, phoneNumber);
		Address newAddress = new Address(street, city, state, zip);
		super.setAddress(newAddress);
		this.memberid = memberid;
	}

	public int getID() {
		return memberid;
	}

	public String toString() {
		return (memberid + ". " + firstName + " " + lastName + " \n" + address + "\n" + phoneNumber);
	}

	public String getName() {
		return (firstName + " " + lastName );
	}
	public void setName(String nm) {
		this.name=nm;
	}
	// Display
	public void display() {

		System.out.println(General.pad(Integer.toString(memberid), 5) + "| "
				+ General.pad(firstName, 15) + "| "
				+ General.pad(lastName, 15) + "| "
				+ General.pad(phoneNumber, 12) + "| "
				+ General.pad(this.address.getStreet(), 15) + "| "
				+ General.pad(this.address.getCity(), 18) + "| "
				+ General.pad(this.address.getState(), 8));
	}

	@Override
	public int compareTo(Member mem) {
		int result = this.firstName.compareTo(mem.firstName);
		return result;
	}
}
