package library.model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import library.General;
import library.core.Book;
import library.core.BookMember;
import library.core.CheckOutRecord;
import library.core.Member;
import library.dataaccess.DataAccessFacade;


public class CheckoutModel {

	final static long START = 10000;
	final static boolean DEBUG = false;
	final static String fileName = "checkouts.dat";

	public static void main(String[] args) {

		List<CheckOutRecord> list = new LinkedList<>();
		list = loadCheckouts();
		display(list);
	}

	public static List<CheckOutRecord> loadCheckouts() {

		List<CheckOutRecord> list = new LinkedList<CheckOutRecord>();

		File f = null;
		boolean fileExist = false;
		try {
			f = new File(General.OUTPUT_DIR + "//" + CheckoutModel.fileName);

			// tests if file exists
			fileExist = f.exists();

			if (fileExist == true) {

				// Load from the file
				list = CheckoutModel.readCheckout();
			} else {
				/* File does not exists */
				// Reset the Checkouts & Save
				CheckoutModel.resetCheckOut(list);
				CheckoutModel.saveCheckOut(list);
				System.out.println(" Checkouts are reset. ");
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return list;
	}

	public static long getNewID(List<CheckOutRecord> list) {
		if (list.size() == 0)
			return START;

		long max = list.get(0).getID();
		for (CheckOutRecord m : list) {
			if (m.getID() >= max) {
				max = m.getID();
			}
		}

		return (max + 1);
	}

	public static long getLastID(List<CheckOutRecord> list) {
		if (list.size() == 0)
			return -1;

		CheckOutRecord tmp = list.get(list.size() - 1);
		return tmp.getID();
	}

	// Find the index from the linked list
	public static int findIndex(List<CheckOutRecord> list, int id) {

		int findIndex = -1; // default
		int counter = 0;
		for (CheckOutRecord c : list) {
			if (id == c.getID()) {
				// Found Match by ID
				findIndex = counter;
				break;
			}
			counter++;
		}

		return findIndex;
	}

	public static void display(List<CheckOutRecord> list) {
		// Display each item
		for (CheckOutRecord c : list) {
			c.display();
		}
	}

	// READ
	public static void displayById(List<CheckOutRecord> list, int id) {

		boolean found = false;
		// Display each
		for (CheckOutRecord c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				found = true;
				c.display();
				break;
			}
		}

		if (!found)
			System.out.println(" No CheckOutRecord is found.");
	}
//nishaB
	// Return CheckOutRecord object by ID
	public static CheckOutRecord getCheckOutById(List<CheckOutRecord> list, int id) {
		CheckOutRecord tmpCheckOutRecord = null;

		for (CheckOutRecord c : list) {
			System.out.println("this is the book"+ c.getBookcopy().getBook().getID());
			if (id == c.getBookcopy().getBook().getID()) {
				// MATCH FOUND
				tmpCheckOutRecord = c;
				break;
			}
		}

		return tmpCheckOutRecord;
	}


	public static List<BookMember> getrecordByMemberId(List<CheckOutRecord> list,int id)
	{
		//CheckOutRecord tmpCheckOutRecord = null;
		List<BookMember> lstmem = new ArrayList<>();

		for (CheckOutRecord c : list) {
			System.out.println("this is the member"+ c.getMember().getID());
			if (id == c.getMember().getID()) {
				String title = c.getBookcopy().getBook().getTitle();
				int bookId = c.getBookcopy().getBook().getID();
				BookMember bookMember = new BookMember(title, bookId);
				// MATCH FOUND
				lstmem.add(new BookMember(title,bookId));
			}
		}

		return lstmem;
	}
	// UPDATE the CheckOutRecord of the given ID
	public static boolean updateCheckOut(List<CheckOutRecord> list, int id, CheckOutRecord newData) {
		// 1. First check if t he id is present or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			CheckOutRecord temp = list.set(index, newData);

			if (DEBUG) {
				System.out.println(" CheckOutRecord is updated!");
				temp.display();
			}

			if (temp.getID() >= START) {
				return true;
			} else {
				return false;
			}

		}
	}

	public static void resetCheckOut(List<CheckOutRecord> list) {

		List<Book> booklist = new LinkedList<Book>();
		booklist = BookModel.loadBooks();

		Book book1 = BookModel.getBookById(booklist, 1000);
		System.out.println(book1.getCopies());

		Book book2 = BookModel.getBookById(booklist, 1001);
		System.out.println(book2.getCopies());

		Book book3 = BookModel.getBookById(booklist, 1002);
		System.out.println(book3.getCopies());

		List<Member> memberlist = new LinkedList<Member>();
		memberlist = MemberModel.loadMembers();

		Member m1 = MemberModel.getMemberById(memberlist, 100);
		Member m2 = MemberModel.getMemberById(memberlist, 101);
		Member m3 = MemberModel.getMemberById(memberlist, 102);

		m1.display();
		m2.display();

		CheckOutRecord checkout1 = null, checkout2 = null, checkout3 = null;

		list.clear();
		try {
			if (!book1.availableBookCopy().equals(null)) {
				LocalDate date1 = LocalDate.of(2016, 1, 18);
				checkout1 = new CheckOutRecord(CheckoutModel.getNewID(list), book1.availableBookCopy(), m1, date1);
				checkout1.getBookcopy().setAvailable(false);

				System.out.println("Checkout entry added!");
				list.add(checkout1);
			}
		} catch (NullPointerException nep) {
			nep.getMessage();
		}

		try {
			if (!book2.availableBookCopy().equals(null)) {
				LocalDate date1 = LocalDate.of(2016, 1, 19);
				checkout2 = new CheckOutRecord(CheckoutModel.getNewID(list), book2.availableBookCopy(), m2, date1);
				checkout2.getBookcopy().setAvailable(false);
				list.add(checkout2);
			}
		} catch (NullPointerException nep) {
			nep.getMessage();
		}

		try {
			if (!book3.availableBookCopy().equals(null)) {
				LocalDate date1 = LocalDate.of(2016, 1, 20);
				checkout3 = new CheckOutRecord(CheckoutModel.getNewID(list), book3.availableBookCopy(), m3, date1);
				checkout3.getBookcopy().setAvailable(false);

				list.add(checkout3);
			}
		} catch (NullPointerException nep) {
			nep.getMessage();
		}

	}

	// SAVE TO THE FILE
	public static void saveCheckOut(List<CheckOutRecord> list) {

		// 1. First Sort it
		// Collections.sort(list);

		// 2. Now Save it the file
		DataAccessFacade dataAccess = new DataAccessFacade();
		dataAccess.saveCheckOut(fileName, list);
	}

	// READ FROM THE FILE
	public static List<CheckOutRecord> readCheckout() {

		// Make CheckOutRecord RW object
		DataAccessFacade dataAccess = new DataAccessFacade();
		return (dataAccess.readCheckout(fileName));
	}



}
