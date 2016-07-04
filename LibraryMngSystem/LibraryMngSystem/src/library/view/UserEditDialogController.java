package library.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.model.UserInterface;
import library.model.UserModel;


/**
 * Dialog to edit details of a user.
 *
 */
public class UserEditDialogController {

	@FXML
	private TextField userIdField;
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passWordField;
	@FXML
	private ComboBox<String> roleField;

	private Stage dialogStage;
	private UserInterface userInterface;
	private boolean okClicked = false;


	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		userIdField.setDisable(true);

		roleField.getItems().addAll(
	            "ADMIN",
	            "LIBRARIAN",
	            "BOTH"
	        );
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
	 * Sets the person to be edited in the dialog.
	 *
	 * @param userInterface
	 */
	public void setPerson(UserInterface userInterface) {
		this.userInterface = userInterface;

		userIdField.setText(Integer.toString(userInterface.getuserID()));
		userNameField.setText(userInterface.getuserName());
		passWordField.setText(userInterface.getpassWord());
		roleField.setValue(userInterface.getRole());

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
	private void handleOk() throws Exception {
		if (isInputValid()) {
			userInterface.setuserID(Integer.parseInt(userIdField.getText()));
			userInterface.setuserName(userNameField.getText());
			//userInterface.setpassWord(passWord);
			userInterface.setpassWord(UserModel.SHAhashing(passWordField.getText()));
			userInterface.setrole(roleField.getValue());

			okClicked = true;
			dialogStage.close();
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

		if (userIdField.getText() == null || userIdField.getText().length() == 0) {
			errorMessage += "No valid User ID code!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(userIdField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Invalid User ID (must be an integer)!\n";
			}
		}

		if (userNameField.getText() == null || userNameField.getText().length() < 4) {
			errorMessage += "No valid UserName!\nUserName should be 4 or more characters\n";
		}

		if (passWordField.getText() == null || passWordField.getText().length() < 4) {
			errorMessage += "No valid password!\nPassword should be 4 or more characters\n";
		}

		if (roleField.getValue() == null || roleField.getValue().length() == 0) {
			errorMessage += "No valid role!\n";
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