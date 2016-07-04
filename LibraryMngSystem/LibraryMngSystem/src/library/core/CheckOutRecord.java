package library.core;

import java.io.Serializable;
import java.time.LocalDate;

import library.General;

public class CheckOutRecord implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6331422718271552577L;

	private long checkoutID; // unique
	private BookCopy bookcopy;
	private Member member;
	private LocalDate checkOutDate;
	private LocalDate dueDate;

	public CheckOutRecord(long checkoutID, BookCopy bookcopy, Member member, LocalDate checkOutDate) {
		this.checkoutID = checkoutID;
		this.bookcopy = bookcopy;
		this.member = member;
		this.checkOutDate = checkOutDate;

		// Due days will come from bookCopy (either 7 or 21)
		this.dueDate = checkOutDate.plusDays(bookcopy.getBook().getMaxCheckoutLength());
	}

	public long getID() {
		return checkoutID;
	}

	public BookCopy getBookcopy() {
		return bookcopy;
	}

	// Returns the memberID
	public Member getMember() {
		return member;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String toString() {
		return (checkoutID + ") " + General.pad(bookcopy.getBook().getISBN(), 19)
				+ General.pad(bookcopy.getBook().getTitle(), 25)
				+ General.pad(member.getFirstName() + " " + member.getLastName(), 22)
				+ General.pad(checkOutDate.toString(), 12) + dueDate.toString());
	}

	// Display
	public void display() {

		System.out.println(General.pad(Long.toString(checkoutID), 6) + "| "
				+ General.pad(bookcopy.getBook().getISBN(), 18) + "| "
				+ General.pad(bookcopy.getBook().getTitle(), 25) + "| "
				+ General.pad(member.getFirstName() + " " + member.getLastName(), 22) + "| "
				+ General.pad(checkOutDate.toString(), 12) + "| "
				+ General.pad(dueDate.toString(), 12));
	}

	// Return display String
	public String display2() {
		return(General.pad(Long.toString(checkoutID), 6) + "| "
				+ General.pad(bookcopy.getBook().getISBN(), 18) + "| "
				+ General.pad(bookcopy.getBook().getTitle(), 25) + "| "
				+ General.pad(member.getFirstName() + " " + member.getLastName(), 22) + "| "
				+ General.pad(checkOutDate.toString(), 12) + "| "
				+ General.pad(dueDate.toString(), 12));
	}
}
