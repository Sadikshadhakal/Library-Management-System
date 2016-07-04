package library.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Checkout.
 *
 */
public class CheckoutInterface {

	private final LongProperty checkoutID;
	private final StringProperty checkOutDate;
	private final StringProperty dueDate;

	/**
	 * Default constructor.
	 */
	public CheckoutInterface() {
		this(0, null, null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param isbnNo
	 * @param title
	 */
	public CheckoutInterface(long checkoutID, String checkOutDate, String dueDate) {

		this.checkoutID = new SimpleLongProperty(checkoutID);
		this.checkOutDate = new SimpleStringProperty(checkOutDate);
		this.dueDate = new SimpleStringProperty(dueDate);
	}

	// CheckoutID
	public long getCheckoutID() {
		return checkoutID.get();
	}

	public void setCheckoutID(long checkoutID) {
		this.checkoutID.set(checkoutID);
	}

	public LongProperty checkoutIDProperty() {
		return checkoutID;
	}

	// CheckoutDate
	public String getCheckOutDate() {
		return checkOutDate.get();
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate.set(checkOutDate);
	}

	public StringProperty checkOutDateProperty() {
		return checkOutDate;
	}

	// DueDate
	public String getDueDate() {
		return dueDate.get();
	}

	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}

	public StringProperty dueDateProperty() {
		return dueDate;
	}

}