package library.core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import library.General;

public class Book implements Serializable, Comparable<Book> {

	private static final long serialVersionUID = -8331172322710112755L;
	private static int idBookCounter = 10000; // for using in BookCopy

	private int id;
	private String ISBN;
	private String title;
	private List<Author> authors = new LinkedList<>();
	private List<BookCopy> copies = new LinkedList<>();
	private int maxCheckoutLength;

	// Gives unique ID to the book copy
	public static synchronized int getUniqueBookID() {
		return idBookCounter++;
	}

	public Book(int id, String iSBN, String title, int maxCheckoutLength) {
		super();
		this.id = id;
		ISBN = iSBN;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
	}

	public void addAuthor(Author author) {
		// add author to the list
		authors.add(author);
	}

	public void addBookCopy(BookCopy bookcopy) {
		// add book copy to the list
		copies.add(bookcopy);
	}
	public void checkoutBookCopy() {
		// add book copy to the list
		BookCopy copy = copies.get(copies.size());
		copy.setAvailable(false);
	}


	// return the available BookCopy
	public BookCopy availableBookCopy() {
		boolean found = false;

		BookCopy theBook = null;
		for (BookCopy bc : copies) {
			//System.out.println(bc.isAvailableYN());
			if (bc.isAvailable()) {
				System.out.println("Found book copy");
				theBook = bc;
				found = true;
				break;	// impo
			}
		}

		if (found) {
			return theBook;
		} else {
			return null;
		}

	}

	public int authorsCount() {
		return authors.size();
	}

	public int bookCount() {
		return copies.size();
	}

	public int getID() {
		return id;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getTitle() {
		return title;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public List<BookCopy> getCopies() {
		return copies;
	}

	public int getCopiesNumber(){
		int numberOfCopies = 0;
		for(BookCopy bc: copies){
			if(bc.isAvailable()){
				System.out.println("Found book copy");
				numberOfCopies++;
			}
		}
		return numberOfCopies;
	}

	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void setCopies(List<BookCopy> copies) {
		this.copies = copies;
	}

	public void setMaxCheckoutLength(int maxCheckoutLength) {
		this.maxCheckoutLength = maxCheckoutLength;
	}

	public String toString() {
		return (General.pad(Integer.toString(id), 5) + "| " + General.pad(ISBN, 21) + "| " + General.pad(title, 25)
				+ "| " + General.pad(Integer.toString(copies.size()), 5) + "| "
				+ General.pad(Integer.toString(authors.size()), 5) + "| "
				+ General.pad(Integer.toString(maxCheckoutLength), 5));
	}

	// Display
	public void display() {

		System.out.println(General.pad(Integer.toString(id), 5) + "| " + General.pad(ISBN, 21) + "| "
				+ General.pad(title, 25) + "| " + General.pad(Integer.toString(copies.size()), 5) + "| "
				+ General.pad(Integer.toString(authors.size()), 5) + "| "
				+ General.pad(Integer.toString(maxCheckoutLength), 5));
	}

	@Override
	public int compareTo(Book book) {
		int result = this.title.compareTo(book.title);
		return result;
	}
}
