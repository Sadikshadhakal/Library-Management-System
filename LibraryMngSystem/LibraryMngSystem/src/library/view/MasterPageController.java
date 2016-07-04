package library.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import library.MainApp;

public class MasterPageController {

	// Reference to the main application
	private MainApp mainApp;

	@FXML
	private Label statusBar;

	public MenuBar mainmenu;

	private boolean menuState = true;

	public Label getStatusBar() {
		return statusBar;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Opens an member dialog.
	 */
	@FXML
	private void memberHandle() {
		if (MainApp.theUser.getRoleString() == "LIBRARIAN") {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Library");
			alert.setHeaderText("ACCESS MEMBER");
			alert.setContentText("LIBRARIAN does not have right \nto access member");

			alert.showAndWait();
		} else {
			// Change the stage
			mainApp.showMemberOverview();
		}
	}


	@FXML
	private void userHandle() {
		if (MainApp.theUser.getRoleString() == "LIBRARIAN") {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Library");
			alert.setHeaderText("ACCESS USER");
			alert.setContentText("Authority is not provided..");

			alert.showAndWait();
		} else {
			// Change the stage
			mainApp.showUserOverview();
		}
	}

	/**
	 * Opens an book dialog.
	 */
	@FXML
	private void handleBook() {
		// Change the stage
		mainApp.showBookOverview();
	}

	/**
	 * Opens an user login dialog
	 */
	@FXML
	private void handleLogout() {
		// Change the stage
		MainApp.theUser = null;

		ObservableList<Menu> myMenus = mainmenu.getMenus();
		for (Menu m : myMenus) {
			ObservableList<MenuItem> items = m.getItems();
			for (MenuItem mi : items) {
				mi.setVisible(false);
			}
			m.setVisible(false);
		}
		mainApp.showLoginDialog();
	}

	/**
	 * Opens an checkout history dialog.
	 */
	@FXML
	private void handleCheckout() {
		// Change the stage
		mainApp.showCheckoutOverview();
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library Application");
		alert.setHeaderText("About");
		alert.setContentText("Developers: \n\tNisha Karanjit\n\tNisha Basnet\n\tNidina Shrestha\n\tSadiksha Dhakal\n\tHadiqa Afzal");

		alert.showAndWait();
	}

	@FXML
	private void handleMenu() {
		ObservableList<Menu> myMenus = mainmenu.getMenus();

		if (this.menuState) {
			for (Menu m : myMenus) {
				ObservableList<MenuItem> items = m.getItems();
				for (MenuItem mi : items) {
					mi.setVisible(false);
				}
				m.setVisible(false);
			}
			menuState = false;
			System.out.println("Hide Menu");
		} else {
			for (Menu m : myMenus) {
				ObservableList<MenuItem> items = m.getItems();
				for (MenuItem mi : items) {
					mi.setVisible(true);
				}
				m.setVisible(true);
			}
			menuState = true;
			System.out.println("Show Menu");
		}
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

	@FXML
	private void handleShowStatistics() {

		int books = MainApp.totalBooks;
		int members = MainApp.totalMembers;
		int users = MainApp.totalUsers;

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Library Application");
		alert.setHeaderText("Statistics");
		alert.setContentText("Users: \t\t" + users + "\nMembers: \t" + members + "\nBooks: \t\t" + books);

		alert.showAndWait();
	}
}