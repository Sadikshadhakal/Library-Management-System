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
import library.core.Role;
import library.core.User;
import library.model.UserInterface;
import library.model.UserModel;

public class UserController {

	private List<User> list = new LinkedList<User>(); // under test we need it

	@FXML
	private TableView<UserInterface> theTable;
	@FXML
	private TableColumn<UserInterface, String> userNameColumn;
	@FXML
	private TableColumn<UserInterface, String> roleColumn;

	@FXML
	private Label idLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private Label passWordLabel;
	@FXML
	private Label roleLabel;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public UserController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		list = UserModel.loadUsers();

		// Initialize the user table with the two columns.
		userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
		roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

		// Clear user details.
		showPersonDetails(null);

		// Listen for selection changes and show the user details when changed.
		theTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->showPersonDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		theTable.setItems(mainApp.getPersonData());
	}

	/**
	 * Fills all text fields to show details about the user. If the specified
	 * user is null, all text fields are cleared.
	 *
	 * @param userInterface
	 *            the user or null
	 */

private void showPersonDetails(UserInterface userInterface) {
		try{
		if (userInterface != null) {
			// Fill the labels with info from the user object.
			System.out.println(userInterface);

			idLabel.setText(Integer.toString(userInterface.getuserID()));
			userNameLabel.setText(userInterface.getuserName());
			//User userInterface;
			passWordLabel.setText(userInterface.getpassWord());

			roleLabel.setText(userInterface.getRole());

		} else {
			idLabel.setText("");
			userNameLabel.setText("");
			passWordLabel.setText("");
			roleLabel.setText("");
		}
		}catch(Exception ex)
		{

		}
	}
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewUser() {

		UserInterface tempPerson = new UserInterface();

		// get the new UserID
		tempPerson.setuserID(UserModel.getNewID(list));

		boolean okClicked = mainApp.showUserEditDialog(tempPerson,
				0); /* 0 for Edit */

		if (okClicked) {
			mainApp.getPersonData().add(tempPerson);

			// add to the our LinkedList Also
			Role theRole;
			if (tempPerson.getRole() == "ADMIN") {
				theRole = Role.ADMIN;
			} else if (tempPerson.getRole() == "LIBRARIAN") {
				theRole = Role.LIBRARIAN;
			} else {
				theRole = Role.BOTH;
			}
			list.add(new User(tempPerson.getuserID(), tempPerson.getuserName(), tempPerson.getpassWord(), theRole));

			// ----- FINALLY SAVE TO THE FILE -----
			UserModel.saveUser(list);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected user.
	 */
	@FXML
	private void handleEditUser() {
		UserInterface selectedPerson = theTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showUserEditDialog(selectedPerson,
					1); /* 1 for Edit */

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
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteUser() {
		int selectedIndex = theTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {

			// DELETE CONTROL -----
			if(theTable.getItems().size()<3) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Delete User");
				alert.setHeaderText("Delete user");
				alert.setContentText("System need at least 2 users.");

				alert.showAndWait();
				return;
			}

			UserInterface deletePerson = theTable.getSelectionModel().getSelectedItem();
			int deleteIndex = deletePerson.getuserID();
			System.out.println(deleteIndex);

			boolean deleteCheck = false;
			deleteCheck = UserModel.deleteUser(list, deleteIndex);
			System.out.println(deleteCheck);

			if (deleteCheck) {
				// Remove from the list temporary
				theTable.getItems().remove(selectedIndex);
				System.out.println(selectedIndex);

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
	}
}