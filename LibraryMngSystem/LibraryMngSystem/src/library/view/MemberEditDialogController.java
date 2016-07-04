package library.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.model.MemberInterface;

/**
 * Dialog to edit details of a user.
 *
 */
public class MemberEditDialogController {

	@FXML
	private TextField memberIdField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField zipField;

	private Stage dialogStage;
	private MemberInterface memberInterface;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		memberIdField.setDisable(true);

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
	 * Sets the member to be edited in the dialog.
	 *
	 * @param memberInterface
	 */
	public void setMember(MemberInterface memberInterface) {
		this.memberInterface = memberInterface;

		memberIdField.setText(Integer.toString(memberInterface.getMemberID()));
		firstNameField.setText(memberInterface.getfirstName());
		lastNameField.setText(memberInterface.getlastName());
		phoneField.setText(memberInterface.getPhone());
		streetField.setText(memberInterface.getStreet());
		cityField.setText(memberInterface.getCity());
		stateField.setText(memberInterface.getState());
		zipField.setText(memberInterface.getZip());
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
	private void handleOk() {
		if (isInputValid()) {
			memberInterface.setMemberID(Integer.parseInt(memberIdField.getText()));
			memberInterface.setfirstName(firstNameField.getText());
			memberInterface.setlastName(lastNameField.getText());
			memberInterface.setPhone(phoneField.getText());
			memberInterface.setStreet(streetField.getText());
			memberInterface.setCity(cityField.getText());
			memberInterface.setState(stateField.getText());
			memberInterface.setZip(zipField.getText());
			memberInterface.setName(firstNameField.getText()+" "+lastNameField.getText());

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

		if (firstNameField.getText() == null || firstNameField.getText().length() < 4) {
			errorMessage += "No valid FirstName!\nFirstName should be 4 or more characters\n";
		}

		if (lastNameField.getText() == null || lastNameField.getText().length() < 4) {
			errorMessage += "No valid LastName!\nLastName should be 4 or more characters\n";
		}

		if (streetField.getText() == null || streetField.getText().length() < 4) {
			errorMessage += "No valid Address!\nStreet address should be 4 or more characters\n";
		}

		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "No valid City!\nCity is required\n";
		}

		if (stateField.getText() == null || stateField.getText().length() == 0) {
			errorMessage += "No valid State!\nState is required\n";
		}

		if (zipField.getText() == null || zipField.getText().length() < 5) {
			errorMessage += "No valid ZipCode (must be 5 letters)!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(zipField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "ZipCode must be a number (eg 52557)!\n";
			}
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