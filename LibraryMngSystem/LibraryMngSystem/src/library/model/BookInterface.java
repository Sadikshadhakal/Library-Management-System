package library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import library.core.Author;

/**
 * Model class for a Book.
 *
 */
public class BookInterface {

	private final IntegerProperty bookID;
	private final StringProperty isbnNo;
	private final StringProperty title;
	private final IntegerProperty checkoutDuration;
	private  Author athr;

//private final StringProperty
	/**
	 * Default constructor.
	 */
	public BookInterface() {
		this(0, null, null, 0);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param isbnNo
	 * @param title
	 */
	public BookInterface(int id, String isbnNo, String title, int checkoutDuration) {

		this.bookID = new SimpleIntegerProperty(id);
		this.isbnNo = new SimpleStringProperty(isbnNo);
		this.title = new SimpleStringProperty(title);
		this.checkoutDuration = new SimpleIntegerProperty(checkoutDuration);

	}

	public int getbookID() {
		return bookID.get();
	}

	public void setbookID(int bookID) {
		this.bookID.set(bookID);
	}

	public IntegerProperty bookIDProperty() {
		return bookID;
	}


	public String getIsbnNo() {
		return isbnNo.get();
	}

	public void setIsbnNo(String isbnNo) {
		this.isbnNo.set(isbnNo);
	}



	public StringProperty isbnNoProperty() {
		return isbnNo;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public int getCheckoutDuration() {
		return checkoutDuration.get();
	}

	public void setCheckoutDuration(int checkoutduration) {
		this.checkoutDuration.set(checkoutduration);
	}

	public IntegerProperty checkoutDurationProperty() {
		return checkoutDuration;
	}

	public Author getAthr() {
		return athr;
	}
	public void setAthr(Author atr) {
		this.athr=atr;
	}

}