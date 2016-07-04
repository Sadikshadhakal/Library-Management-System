package library.view;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import library.MainApp;
import library.core.Author;
import library.core.Book;
import library.core.BookCopy;
import library.core.Role;
import library.core.User;
import library.model.BookInterface;
import library.model.BookModel;
import library.model.UserInterface;
import library.model.UserModel;

public class BookOverviewController {

	private List<Book> list = new LinkedList<Book>();

	@FXML
	private TableView<BookInterface> theTable;
	@FXML
	private TableColumn<BookInterface, Number> bkColumn;
	@FXML
	private TableColumn<BookInterface, String> titleColumn;

	@FXML
	private Label idLabel;
	@FXML
	private Label isbnLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private Label durationLabel;


	@FXML
	private Label copiesLabel;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public BookOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		list = BookModel.loadBooks();

		// Initialize the book table with the two columns.
		bkColumn.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty());
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

		// Clear book details.
		showBookDetails(null);

		// Listen for selection changes and show the book details when changed.
		theTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showBookDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;


		theTable.setItems(mainApp.getBookData());
	}


	public List<Book> getList() {
		return list;
	}

	/**
	 * Fills all text fields to show details about the user. If the specified
	 * book is null, all text fields are cleared.
	 *
	 * @param bookInterface
	 *            the book or null
	 */
	private void showBookDetails(BookInterface bookInterface) {
		if (bookInterface != null) {
			// Fill the labels with info from the book object.
			System.out.println(bookInterface);

			idLabel.setText(Integer.toString(bookInterface.getbookID()));
			isbnLabel.setText(bookInterface.getIsbnNo());
			titleLabel.setText(bookInterface.getTitle());
			durationLabel.setText(Integer.toString(bookInterface.getCheckoutDuration()));
			//athrLabel.setText(bookInterface.getAthr().getName());
			Book bk = BookModel.getBookById(list, bookInterface.getbookID());
			int bookCopies = bk.getCopiesNumber();
			//int abc=bookCopies.

			copiesLabel.setText(Integer.toString(bookCopies));

		} else {
			idLabel.setText("");
			isbnLabel.setText("");
			titleLabel.setText("");
			durationLabel.setText("");
			copiesLabel.setText("");
		}
	}
@FXML
private void handleCheckin(){
	if (MainApp.theUser.getRoleString() == "ADMIN") {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Library");
		alert.setHeaderText("BOOK CHECKOUT");
		alert.setContentText("ADMIN does not have right to checkin the book");

		alert.showAndWait();
		return;

	}
	boolean onclicked= mainApp.showCheckinDialog();


}

	/**
	 * Called when the book clicks the new button. Opens a dialog to edit
	 * details for a new book.
	 */
	@FXML
	private void handleNewBook() {

		BookInterface tempBook = new BookInterface();

		// get the new BookID ------
		tempBook.setbookID(BookModel.getNewID(list));

		/* 0 for Edit */
		boolean okClicked = mainApp.showBookEditDialog(tempBook, 0);

		if (okClicked) {
			mainApp.getBookData().add(tempBook);



			Book newBook = new Book(tempBook.getbookID(), tempBook.getIsbnNo(), tempBook.getTitle(),
					tempBook.getCheckoutDuration());
			newBook.addAuthor(tempBook.getAthr()); // Write now static author is added

			BookCopy bookcopy;
			bookcopy = new BookCopy(Book.getUniqueBookID() + MainApp.totalBooks + 1);
			bookcopy.setBook(newBook);
			newBook.addBookCopy(bookcopy);

			list.add(newBook);

			// ----- FINALLY SAVE TO THE FILE -----
			BookModel.saveBook(list);
			BookModel.display(list);
		}
	}

	/**
	 * Called when the book clicks the edit button. Opens a dialog to edit
	 * details for the selected book.
	 */
	@FXML
	private void handleEditBook() {
		BookInterface selectedBook = theTable.getSelectionModel().getSelectedItem();
		if (selectedBook != null) {
			/* 1 for Edit */
			boolean okClicked = mainApp.showBookEditDialog(selectedBook, 1);

			if (okClicked) {
				showBookDetails(selectedBook);

				// add to the our LinkedList Also

				boolean editCheck = false;
				int editID = selectedBook.getbookID();

				Book oldBook = BookModel.getBookById(list, editID);

				Book updBook = new Book(editID, selectedBook.getIsbnNo(), selectedBook.getTitle(),
						selectedBook.getCheckoutDuration());
				updBook.setAuthors(oldBook.getAuthors());
				updBook.setCopies(oldBook.getCopies());

				editCheck = BookModel.updateBook(list, editID, updBook);
				System.out.println(editCheck);
				BookModel.saveBook(list);
				BookModel.display(list);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Book Selected");
			alert.setContentText("Please select a book in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleAddCopy() {

		if (MainApp.theUser.getRoleString() == "ADMIN") {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Library");
			alert.setHeaderText("ADD BOOK COPY");
			alert.setContentText("ADMIN does not have right \nto add book copy");

			alert.showAndWait();
			return;
		}

		BookInterface selectedBook = theTable.getSelectionModel().getSelectedItem();
		if (selectedBook != null) {

			showBookDetails(selectedBook);

			// add to the our LinkedList Also

			boolean editCheck = false;
			int editID = selectedBook.getbookID();

			Book oldBook = BookModel.getBookById(list, editID);

			Book updBook = new Book(editID, selectedBook.getIsbnNo(), selectedBook.getTitle(),
					selectedBook.getCheckoutDuration());
			updBook.setAuthors(oldBook.getAuthors());
			updBook.setCopies(oldBook.getCopies());

			// For Adding Multiple Copies (as well) ------
			for (int i = 1; i <= 1; i++) {
				BookCopy bookCopy = new BookCopy(Book.getUniqueBookID() + MainApp.totalBooks + 1);
				bookCopy.setBook(updBook);
				updBook.addBookCopy(bookCopy);
			}

			editCheck = BookModel.updateBook(list, editID, updBook);
			System.out.println(editCheck);
			if (editCheck) {
				BookModel.saveBook(list);
				BookModel.display(list);

				// update the copies label
				Integer num=Integer.parseInt(copiesLabel.getText())+1;
				copiesLabel.setText(String.valueOf(num));
				//(Integer.toString(updBook.getCopies().size()));
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Book Selected");
			alert.setContentText("Please select a book in the table.");

			alert.showAndWait();
		}
	}

	public void checkoutrefresh()
	{
		copiesLabel.setText(String.valueOf(Integer.parseInt(copiesLabel.getText())-1));


	}


	@FXML
	private void handleCheckout() {

		if (MainApp.theUser.getRoleString() == "ADMIN") {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Library");
			alert.setHeaderText("BOOK CHECKOUT");
			alert.setContentText("ADMIN does not have right \nto checkout the book");

			alert.showAndWait();
			return;
		}

		BookInterface selectedBook = theTable.getSelectionModel().getSelectedItem();

		if (selectedBook != null) {
			int bookID = selectedBook.getbookID();
			Book theBook = BookModel.getBookById(list, bookID);
			boolean okClicked = mainApp.showCheckoutDialog(theBook);

			if (okClicked) {

				copiesLabel.setText(String.valueOf(Integer.parseInt(copiesLabel.getText())-1));
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Book Checkout");
				alert.setHeaderText("Book Checkout");
				alert.setContentText("The selected book is successfully checked out.");

				alert.showAndWait();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Book Selected");
			alert.setContentText("Please select a book in the table.");

			alert.showAndWait();
		}

	}


	/*@FXML
	private void handleEditUser() {
		UserInterface selectedPerson = theTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showUserEditDialog(selectedPerson,
					1);  1 for Edit

			if (okClicked) {
				showPersonDetails(selectedPerson);

				// add to the our LinkedList Also
				Role theRole;
				if (selectedPerson.getRole() == "ADMIN") {
					theRole = Role.ADMIN;
				} else if (selectedPerson.getRole() == "LIBRARIAN") {
					theRole = Role.LIBRARIAN;
				} else {
					theRole = Role.BOTH;
				}

				boolean editCheck = false;
				int editID = selectedPerson.getuserID();
				editCheck = UserModel.updateUser(list, editID,
						new User(editID, selectedPerson.getuserName(), selectedPerson.getpassWord(), theRole));
				System.out.println(editCheck);
				UserModel.saveUser(list);
				UserModel.display(list);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No User Selected");
			alert.setContentText("Please select a user in the table.");

			alert.showAndWait();
		}
	}*/






}