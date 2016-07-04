package library.model;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import library.General;
import library.core.Role;
import library.core.User;
import library.dataaccess.DataAccessFacade;


public class UserModel {

	final static int START = 100;
	final static boolean DEBUG = false;
	final static String fileName = "users.dat";

	public static void main(String[] args) {


		List<User> list = new LinkedList<User>();
		list = loadUsers();
		display(list);
	}

	public static List<User> loadUsers() {

		List<User> list = new LinkedList<User>();

		File f = null;
		boolean fileExist = false;
		try {
			f = new File(General.OUTPUT_DIR + "//" + UserModel.fileName);

			// tests if file exists
			fileExist = f.exists();
			// System.out.println(fileExist);

			if (fileExist == true) {

				// Load from the file
				list = UserModel.readUser();
			} else {
				/* File Does not exists */
				// Reset the Users & Save
				UserModel.reset(list);
				UserModel.saveUser(list);
				System.out.println(" Users are reset. ");
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return list;
	}

	public static int getNewID(List<User> list) {
		if (list.size() == 0)
			return START;

		int max = list.get(0).getID();
		for (User m : list) {
			if (m.getID() >= max) {
				max = m.getID();
			}
		}

		return (max + 1);
	}

	public static int getLastID(List<User> list) {
		if (list.size() == 0)
			return -1;

		User tmp = list.get(list.size() - 1);
		return tmp.getID();
	}

	// Find the index from the linked list
	public static int findIndex(List<User> list, int id) {

		int findIndex = -1; // default
		int counter = 0;
		for (User c : list) {
			if (id == c.getID()) {
				// MATCH FOUND by ID
				findIndex = counter;
				break;
			}
			counter++;
		}

		return findIndex;
	}

	public static void display(List<User> list) {
		// Display each item
		for (User c : list) {
			c.display();
		}
	}

	// READ
	public static void displayById(List<User> list, int id) {

		boolean found = false;
		// Display each
		for (User c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				found = true;
				c.display();
				break;
			}
		}

		if (!found)
			System.out.println(" No User is found.");
	}

	// DELETE the User of the given ID
	public static boolean deleteUser(List<User> list, int id) {
		// 1. First check if the id is present or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			User temp;
			temp = list.remove(index);

			if (DEBUG) {
				System.out.println(" Removed item!");
				temp.display();
			}

			if (temp.getID() >= START) {
				return true;
			} else {
				return false;
			}

		}
	}

	// UPDATE the User of the given ID
	public static boolean updateUser(List<User> list, int id, User newData) {
		// 1. First check if the id is present or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			User temp = list.set(index, newData);

			if (DEBUG) {
				System.out.println(" User is updated!");
				temp.display();
			}

			if (temp.getID() >= START) {
				return true;
			} else {
				return false;
			}

		}
	}

	public static void reset(List<User> list) throws Exception {

		list.clear();
		// Add User to the list
		list.add(new User(UserModel.getNewID(list), "nisha", SHAhashing("nisha"), Role.ADMIN));
		list.add(new User(UserModel.getNewID(list), "nidina", SHAhashing("nidina"), Role.LIBRARIAN));
		list.add(new User(UserModel.getNewID(list), "sadiksha", SHAhashing("sadiksha"), Role.BOTH));
	}

	// SAVE TO THE FILE
	public static void saveUser(List<User> list) {

		// 1. Now Save it the file
		DataAccessFacade dataAccess = new DataAccessFacade();
		dataAccess.saveUser(fileName, list);
	}

	// READ FROM THE FILE
	public static List<User> readUser() {

		// Make User RW object
		DataAccessFacade dataAccess = new DataAccessFacade();
		return (dataAccess.readUser(fileName));
	}

	//Sha algorithm for password encryption

	public static String SHAhashing(String password) throws NoSuchAlgorithmException

	{

    MessageDigest md = MessageDigest.getInstance("SHA-256");
    md.update(password.getBytes());

    byte byteData[] = md.digest();

    //convert the byte to hex format method 1
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < byteData.length; i++) {
     sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }

    System.out.println("Hex format : " + sb.toString());

    //convert the byte to hex format method 2
    StringBuffer hexString = new StringBuffer();
	for (int i=0;i<byteData.length;i++) {
		String hex=Integer.toHexString(0xff & byteData[i]);
	     	if(hex.length()==1) hexString.append('0');
	     	hexString.append(hex);
	}
	System.out.println("Hex format : " + hexString.toString());
	return hexString.toString();
}


	// for generating MD5 password
//	public static String MD5(String passwordToHash) {
//		String generatedPassword = null;
//		try {
//			// Create MessageDigest instance for MD5
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(passwordToHash.getBytes());
//			byte[] bytes = md.digest();
//
//			// This bytes[] has bytes in decimal format;
//			// Convert it to hexadecimal format
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			// Get complete hashed password in hex format
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
}
