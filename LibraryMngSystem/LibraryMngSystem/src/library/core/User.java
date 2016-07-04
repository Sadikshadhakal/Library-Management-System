package library.core;

import java.io.Serializable;

import library.General;

public class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5135427034388034840L;

	private int id;
	private String username;
	private String password;
	private Role role;

	public User(int id, String username, String password, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getID() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public String getRoleString() {
		String theRole="";
		if(role==Role.ADMIN) {
			theRole = "ADMIN";
		} else if(role==Role.LIBRARIAN) {
			theRole = "LIBRARIAN";
		} else if (role==Role.BOTH) {
			theRole = "BOTH";
		}
		return theRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}

	// Display
	public void display() {

		String theRole = this.getRoleString();

		System.out.println(General.pad(Integer.toString(id), 5) + "| "
				+ General.pad(username, 15) + "| "
				+ General.pad(password, 33) + "| "
				+ General.pad(theRole, 10));
	}

}
