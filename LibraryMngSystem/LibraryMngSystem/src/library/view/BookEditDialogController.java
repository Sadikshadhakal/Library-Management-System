package library.view;

import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.core.Author;
import library.model.BookInterface;
import library.model.BookModel;

/**
 * Dialog to edit details of a book.
 *
 */
public class BookEditDialogController {

	@FXML
	private TextField bookIdField;
	@FXML
	private TextField isbnField;
	@FXML
	private TextField titleField;
	@FXML
	private ComboBox<String> durationField;


	@FXML
	private ComboBox<String> authorField;

	private Stage dialogStage;
	private BookInterface bookInterface;
	private boolean okClicked = false;


	@FXML
	private void initialize() {
		List<Author> lstauthor=BookModel.GetlistAuthor();
		bookIdField.setDisable(true);
HashMap<Integer,String> hsp=new HashMap<>();
		for(Author a:lstauthor)
		{
			hsp.put(a.getId(),a.getName());

			authorField.getItems().add(a.getName());
		}

		durationField.getItems().addAll("7", "21");
	}


	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;

		// Set the dialog icon.
		this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
	}


	public void setBook(BookInterface bookInterface) {
		this.bookInterface = bookInterface;

		bookIdField.setText(Integer.toString(bookInterface.getbookID()));
		isbnField.setText(bookInterface.getIsbnNo());
		titleField.setText(bookInterface.getTitle());
		durationField.setValue(Integer.toString(bookInterface.getCheckoutDuration()));

	}


	public boolean isOkClicked() {
		return okClicked;
	}


	@FXML
	private void handleOk() {
		List<Author> lstauthor=BookModel.GetlistAuthor();
		String author=authorField.getValue();
		for(Author ap:lstauthor)
		{
			if(ap.getName().equals(author))
			{
				bookInterface.setAthr(ap);

			}

		}
		if (isInputValid()) {
			bookInterface.setbookID(Integer.parseInt(bookIdField.getText()));
			bookInterface.setIsbnNo(isbnField.getText());
			bookInterface.setTitle(titleField.getText());
			bookInterface.setCheckoutDuration(Integer.parseInt(durationField.getValue()));

			okClicked = true;
			dialogStage.close();
		}
	}


	@FXML
	private void handleCancel() {
		dialogStage.close();
	}


	private boolean isInputValid() {
		String errorMessage = "";

		if (bookIdField.getText() == null || bookIdField.getText().length() == 0) {
			errorMessage += "No valid User ID code!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(bookIdField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Invalid User ID (must be an integer)!\n";
			}
		}

		if (isbnField.getText() == null || isbnField.getText().length() < 10) {
			errorMessage += "No valid ISBN!\nISBN should be 17 characters long\n";
		}

		if (titleField.getText() == null || titleField.getText().length() < 4) {
			errorMessage += "No valid book title!\nBook title should be 4 or more characters\n";
		}

		if (durationField.getValue() == null || durationField.getValue().length() == 0) {
			errorMessage += "Book checkout duration must be 7 or 21 days!\n";
		} else {

			try {
				Integer.parseInt(durationField.getValue());
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Checkout duration (must be an integer)!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields .........S");
			alert.setHeaderText("Please check invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}