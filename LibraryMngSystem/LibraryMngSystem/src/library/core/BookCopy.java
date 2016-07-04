package library.core;

import java.io.Serializable;

public class BookCopy implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3340811843694512117L;

	private int copynumber;
	private boolean available;
	private Book book;
	
	public BookCopy(int copynumber) {
		this.copynumber = copynumber;
		available = true;
	}

	public int getCopynumber() {
		return copynumber;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Book getBook() {
		return book;
	}

	// change the availability of the book
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

	public String isAvailableYN() {
		if (available == true)
			return "Yes";
		else
			return "No";
	}

	public String toString() {
		return (Integer.toString(copynumber));
	}
}
