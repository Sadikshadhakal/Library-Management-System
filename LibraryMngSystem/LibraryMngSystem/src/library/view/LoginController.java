package library.view;

import java.util.LinkedList;
import java.util.List;

import dataaccess.storage.staticdata;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import library.MainApp;
import library.core.User;
import library.model.UserModel;


public class LoginController {

	private List<User> users = new LinkedList<User>();

	// private Stage dialogStage;

	private boolean okClicked = false;

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label labelMessage;
	@FXML
	private Button btnLogin;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public LoginController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * @throws Exception
	 */
	@FXML
	private void initialize() throws Exception {

		users = UserModel.loadUsers();

		//users= staticdata.getUser();


		// Clear book details.
		usernameField.setText("");
		passwordField.setText("");

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
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleLogin() throws Exception {

		boolean userfound = false;

		for (User u : users) {

			String uname = usernameField.getText();
			String pword= UserModel.SHAhashing((passwordField.getText()));
			if (uname.equals(u.getUsername()) && pword.equals(u.getPassword())) {
				userfound = true;
				MainApp.theUser = u; // the user that login into the system
				break;
			}

		}

		if (userfound) {
			okClicked = true;
			labelMessage.setText("Success (User Found)");

		} else {
			labelMessage.setText("Username or Password is incorrect");

		}

		if (userfound) {

			/* Hide all menu items (START) */
			for (Menu m : mainApp.myMenus) {
				ObservableList<MenuItem> items = m.getItems();
				for (MenuItem mi : items) {
					mi.setVisible(true);
				}
				m.setVisible(true);
			}
			/* Hide all menu items (ENDS) */

			// Change Status bar
			mainApp.statusBar.setText("Library Management Application  | " + MainApp.theUser.getUsername() + " ["
					+ MainApp.theUser.getRoleString() + "] ");
			mainApp.showBookOverview();

		}
	}

	@FXML
	private void handleCancel() {
		// dialogStage.close();
		System.exit(0);
	}

}