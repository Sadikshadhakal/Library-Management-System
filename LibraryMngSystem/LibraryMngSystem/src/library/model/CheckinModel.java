package library.model;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import library.General;
import library.core.Book;
import library.core.CheckOutRecord;
import library.core.Member;
import library.dataaccess.DataAccessFacade;

public class CheckinModel {
	final static long START = 10000;
	final static boolean DEBUG = false;
	final static String fileName = "checkouts.dat";

	public static void main(String[] args) {

		List<CheckOutRecord> list = new LinkedList<>();
		list = loadCheckouts();
		//display(list);
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

	
	// Return CheckOutRecord object by ID
	public static CheckOutRecord getCheckOutById(List<CheckOutRecord> list, int id) {
		CheckOutRecord tmpCheckOutRecord = null;

		for (CheckOutRecord c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				tmpCheckOutRecord = c;
				break;
			}
		}

		return tmpCheckOutRecord;
	}

	// UPDATE the CheckOutRecord of the given ID
	
	// READ FROM THE FILE
	public static List<CheckOutRecord> readCheckout() {

		// Make CheckOutRecord RW object
		DataAccessFacade dataAccess = new DataAccessFacade();
		return (dataAccess.readCheckout(fileName));
	}

}
