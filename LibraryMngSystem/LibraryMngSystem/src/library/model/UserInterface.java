package library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a User.
 *
 */
public class UserInterface {

	private final IntegerProperty userID;
	private final StringProperty userName;
	private final StringProperty passWord;
	private final StringProperty role;

	/**
	 * Default constructor.
	 */
	public UserInterface() {
		this(0, null, null, null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param userName
	 * @param passWord
	 */
	public UserInterface(int id, String userName, String passWord, String role) {

		this.userID = new SimpleIntegerProperty(id);
		this.userName = new SimpleStringProperty(userName);
		this.passWord = new SimpleStringProperty(passWord);
		this.role = new SimpleStringProperty(role);
	}

	public String getuserName() {
		return userName.get();
	}

	public void setuserName(String userName) {
		this.userName.set(userName);
	}

	public StringProperty userNameProperty() {
		return userName;
	}

	public String getpassWord() {
		return passWord.get();
	}

	public void setpassWord(String passWord) {
		this.passWord.set(passWord);
	}

	public StringProperty passWordProperty() {
		return passWord;
	}

	public String getRole() {
		return role.get();
	}

	public void setrole(String role) {
		this.role.set(role);
	}

	public StringProperty roleProperty() {
		return role;
	}

	public int getuserID() {
		return userID.get();
	}

	public void setuserID(int userID) {
		this.userID.set(userID);
	}

	public IntegerProperty userIDProperty() {
		return userID;
	}

}