package library.model;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import library.General;
import library.core.Member;
import library.dataaccess.DataAccessFacade;

public class MemberModel {

	final static int START = 100;
	final static boolean DEBUG = false;
	final static String fileName = "members.dat";

	public static void main(String[] args) {

		List<Member> list = new LinkedList<Member>();
		list = loadMembers();
		display(list);

	}

	public static List<Member> loadMembers() {

		List<Member> list = new LinkedList<Member>();

		File f = null;
		boolean fileExist = false;
		try {
			f = new File(General.OUTPUT_DIR + "//" + MemberModel.fileName);


			fileExist = f.exists();


			if (fileExist == true) {

				// Load from the file
				list = MemberModel.readMember();
			} else {
				/* File Does not exists */
				// Reset the Members & Save
				MemberModel.resetMember(list);
				MemberModel.saveMember(list);
				System.out.println(" Members are reset. ");
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return list;
	}

	public static int getNewID(List<Member> list) {
		if (list.size() == 0)
			return START;

		int max = list.get(0).getID();
		for (Member m : list) {
			if (m.getID() >= max) {
				max = m.getID();
			}
		}

		return (max + 1);
	}

	public static int getLastID(List<Member> list) {
		if (list.size() == 0)
			return -1;

		Member tmp = list.get(list.size() - 1);
		return tmp.getID();
	}

	// Find the index from the linked list
	public static int findIndex(List<Member> list, int id) {

		int findIndex = -1; // default
		int counter = 0;
		for (Member c : list) {
			if (id == c.getID()) {
				// FOUND MATCH by ID
				findIndex = counter;
				break;
			}
			counter++;
		}

		return findIndex;
	}

	public static void display(List<Member> list) {
		// Display each item
		for (Member c : list) {
			c.display();
		}
	}

	// READ
	public static void displayById(List<Member> list, int id) {

		boolean found = false;
		// Display each
		for (Member c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				found = true;
				c.display();
				break;
			}
		}

		if (!found)
			System.out.println(" No member is found.");
	}

	// Return member object by ID
	public static Member getMemberById(List<Member> list, int id) {
		Member tmpBook = null;

		for (Member c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				tmpBook = c;
				break;
			}
		}

		return tmpBook;
	}

	// DELETE the Member of the given ID
	public static boolean deleteMember(List<Member> list, int id) {
		// 1. First check if the id is present or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			Member temp;
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

	// UPDATE MEMBER
	public static boolean updateMember(List<Member> list, int id, Member newData) {
		// Check whether id exist or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			Member temp = list.set(index, newData);

			if (DEBUG) {
				System.out.println(" Member is updated!");
				temp.display();
			}

			if (temp.getID() >= START) {
				return true;
			} else {
				return false;
			}

		}
	}

	public static void resetMember(List<Member> list) {

		list.clear();
		// Member List
		list.add(new Member(MemberModel.getNewID(list), "Hadiqa", "Afzal", "9841414245", "Sgnapur", "Pakistan",
				"Bagmati", "NA"));
		list.add(new Member(MemberModel.getNewID(list), "Nisha", "Karanjit", "9851242424", "MangalBazar", "Lalitpur",
				"Nepal", "52587"));
		list.add(new Member(MemberModel.getNewID(list), "Nisha", "Basnet", "984895656", "Lubhoo", "Lalitpur",
				"Nepal", "54674"));
		list.add(new Member(MemberModel.getNewID(list), "Nidina ", "Shrestha", "6587459566", "Chabel",
				"Kathmandu", "Nepal", "55555"));
		list.add(new Member(MemberModel.getNewID(list), "Sadiksha ", "Dhakal", "6587459566", "Samakhusi",
				"Kathmandu", "Nepal", "55555"));

	}

	// SAVE MEMBER
	public static void saveMember(List<Member> list) {

		// Sort List
		Collections.sort(list);

		// Save in file
		DataAccessFacade dataAccess = new DataAccessFacade();
		dataAccess.saveMember(fileName, list);
	}

	// READ FILE
	public static List<Member> readMember() {


		DataAccessFacade dataAccess = new DataAccessFacade();
		return (dataAccess.readMember(fileName));
	}

}
