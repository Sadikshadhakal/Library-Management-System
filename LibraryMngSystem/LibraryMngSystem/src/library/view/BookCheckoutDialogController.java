package library.view;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.core.Book;
import library.core.BookCopy;
import library.core.CheckOutRecord;
import library.core.Member;
import library.model.BookModel;
import library.model.CheckoutModel;
import library.model.MemberModel;

/**
 * Dialog to edit details of a book.
 *
 */
public class BookCheckoutDialogController {

	private List<CheckOutRecord> list = new LinkedList<CheckOutRecord>();

	@FXML
	private TextField memberIdField;
	@FXML
	private Label memberField;
	@FXML
	private DatePicker issueDateField;

	private Member member;
	private Book book;
	private int checkoutnum;

	private Stage dialogStage;
	// private CheckoutInterface checkoutInterface;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		member = null;
		list= CheckoutModel.loadCheckouts();
		LocalDate today = LocalDate.now();
		issueDateField.setValue(today);

	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

		// Set the dialog icon.
		this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
	}

	/**
	 * Sets the book variable
	 *
	 * @param book
	 */
	public void setCheckoutBook(Book book) {
		this.book = book;
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks OK.
	 */
	@FXML
	private void handleCheckout() {
		if (isInputValid()) {


			CheckOutRecord checkout = null;

			try {


				if (!book.availableBookCopy().equals(null)) {
					checkout = new CheckOutRecord(CheckoutModel.getNewID(list), book.availableBookCopy(), member,
							issueDateField.getValue());
					checkout.getBookcopy().setAvailable(false); // important
//BookModel bm=new BookModel();
					 List<Book> lstbk=BookModel.readBook();
					 Book oldBook = BookModel.getBookById(lstbk, book.getID());

		  List<BookCopy> lstbkcpy=oldBook.getCopies();
		  for(BookCopy b:lstbkcpy)

		  {
			  if(b.isAvailable())
			  {
				  b.setAvailable(false);
				  break;
			  }


		  }
		 // List<Book> lstbk=BookModel.readBook();


			boolean editCheck = BookModel.updateBook(lstbk, oldBook.getID(), oldBook);
			System.out.println(editCheck);
			System.out.println(editCheck);
			BookModel.saveBook(lstbk);
			BookModel.display(lstbk);



//		  BookModel.saveBook(list);
					System.out.println("Checkout entry added!");

					list.add(checkout);

					//Save to file
					CheckoutModel.saveCheckOut(list);




					okClicked = true;

				}
			} catch (NullPointerException nep) {
				nep.getMessage();
				okClicked = false;
			}

			dialogStage.close();
		}
	}

	@FXML
	private void handleFind() {

		// Find the member by ID
		int memberID = -1;
		memberID = Integer.parseInt(memberIdField.getText());

		List<Member> list = new LinkedList<Member>();
		list = MemberModel.loadMembers();

		Member m = MemberModel.getMemberById(list, memberID);

		try {
			if (!m.equals(null)) {
				MemberModel.display(list);
				memberField.setText(m.getFirstName() + " " + m.getLastName() + " is found.");
				this.member = m;
			}
		} catch (NullPointerException ne) {
			memberField.setText("<<MEMBER IS NOT FOUND >>");
			ne.getMessage();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (memberIdField.getText() == null || memberIdField.getText().length() == 0) {
			errorMessage += "No valid Member ID code!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(memberIdField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Member ID (must be an integer)!\n";
			}
		}

		if (issueDateField.getValue() == null) {
			errorMessage += "No valid date selected!";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}