package library.core;

import java.io.Serializable;
import java.time.LocalDate;

import library.General;

public class CheckInRecord implements Serializable {

	private static final long serialVersionUID = -6331422718271552577L;

	private long checkinID; // unique
	private BookCopy bookcopy;
	private Member member;
	//private LocalDate checkOutDate;
	//private LocalDate dueDate;

	public CheckInRecord(long checkinID, BookCopy bookcopy, Member member) {
		this.checkinID = checkinID;
		this.bookcopy = bookcopy;
		this.member = member;
		//this.checkOutDate = checkOutDate;

		// Due days will come from bookCopy (either 7 or 21)
		//this.dueDate = checkOutDate.plusDays(bookcopy.getBook().getMaxCheckoutLength());
	}

	public long getID() {
		return checkinID;
	}

	public BookCopy getBookcopy() {
		return bookcopy;
	}

	// Returns the memberID
	public Member getMember() {
		return member;
	}

		}
