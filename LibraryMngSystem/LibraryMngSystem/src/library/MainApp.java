package library;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.core.Address;
import library.core.Book;
import library.core.Member;
import library.core.User;
import library.model.BookInterface;
import library.model.BookModel;
import library.model.MemberInterface;
import library.model.MemberModel;
import library.model.UserInterface;
import library.model.UserModel;
import library.view.BookCheckoutDialogController;
import library.view.BookOverviewController;
import library.view.CheckInController;
import library.view.BookEditDialogController;
import library.view.CheckoutController;
import library.view.LoginController;
import library.view.MemberController;
import library.view.MemberEditDialogController;
import library.view.MasterPageController;
import library.view.UserController;
import library.view.UserEditDialogController;

public class MainApp extends Application {

	private static final String LINE_BREAK = "--------------------------------------------------------------";

	public static int totalUsers = 0;
	public static int totalBooks = 0;
	public static int totalMembers = 0;

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of Users, Members
	 */
	private ObservableList<UserInterface> userData = FXCollections.observableArrayList();
	private ObservableList<MemberInterface> memberData = FXCollections.observableArrayList();
	private ObservableList<BookInterface> bookData = FXCollections.observableArrayList();

	public ObservableList<Menu> myMenus = null;
	public Label statusBar = null;

	/* Current USER */
	public static User theUser;

	/**
	 * Constructor
	 */
	public MainApp() {

		List<User> list = new LinkedList<User>();

		list = UserModel.loadUsers();
		totalUsers = list.size();


		System.out.println(MainApp.LINE_BREAK);

		for (User u : list) {

			userData.add(new UserInterface(u.getID(), u.getUsername(), u.getPassword(), u.getRoleString()));
		}


		List<Member> mlist = new LinkedList<Member>();

		mlist = MemberModel.loadMembers();
		totalMembers = mlist.size();

		MemberModel.display(mlist);
		System.out.println(MainApp.LINE_BREAK);

		for (Member m : mlist) {

			Address tA = m.getAddressObj();

			memberData.add(new MemberInterface(m.getID(), m.getFirstName(), m.getLastName(), m.getPhoneNumber(),
					tA.getStreet(), tA.getCity(), tA.getState(), tA.getZip()));
		}


		List<Book> blist = new LinkedList<Book>();

		blist = BookModel.loadBooks();
		totalBooks = blist.size();

		BookModel.display(blist);
		System.out.println(MainApp.LINE_BREAK);

		for (Book b : blist) {

			bookData.add(new BookInterface(b.getID(), b.getISBN(), b.getTitle(), b.getMaxCheckoutLength()));
		}
	}


	public ObservableList<UserInterface> getPersonData() {
		return userData;
	}

	public ObservableList<MemberInterface> getMemberData() {
		return memberData;
	}

	public ObservableList<BookInterface> getBookData() {
		return bookData;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		/*
		 * LOGIN DISPLAY Parts Begins
		 */

		this.primaryStage = primaryStage;
		initRootLayout();

		showLoginDialog();
		// showBookOverview(); /* Book or User or Member can also be displayed
		// at first */
	}

	/**
	 * Initializes the root layout and tries to load the last opened person
	 * file.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MasterPage.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout, 1200, 768);
			primaryStage.setTitle("Library Management Application");
			primaryStage.getIcons().add(new Image("file:resources/images/library.png"));
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			MasterPageController controller = loader.getController();
			controller.setMainApp(this);

			myMenus = controller.mainmenu.getMenus(); // important
			statusBar = controller.getStatusBar();

			/* Hide all menu items (START) */
			for (Menu m : myMenus) {
				ObservableList<MenuItem> items = m.getItems();
				for (MenuItem mi : items) {
					mi.setVisible(false);
				}
				m.setVisible(false);
			}
			/* Hide all menu items (ENDS) */

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLoginDialog() {
		try {

			// Load User overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/LoginDialog.fxml"));
			AnchorPane userOverview = (AnchorPane) loader.load();

			// Set user overview into the center of root layout.
			rootLayout.setCenter(userOverview);

			// Give the controller access to the main app.
			LoginController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the user overview inside the root layout.
	 */
	public void showUserOverview() {
		try {
			// Load user overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/UserOverview.fxml"));
			AnchorPane theOverview = (AnchorPane) loader.load();

			// Set user overview into the center of root layout.
			rootLayout.setCenter(theOverview);

			// Give the controller access to the main app.
			UserController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the member overview inside the root layout.
	 */
	public void showMemberOverview() {
		try {

			// Load member overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MemberOverview.fxml"));
			AnchorPane theOverview = (AnchorPane) loader.load();

			// Set member overview into the center of root layout.
			rootLayout.setCenter(theOverview);

			// Give the controller access to the main app.
			MemberController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the book overview inside the root layout.
	 */
	public void showBookOverview() {
		try {

			// Load book overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BookOverview.fxml"));
			AnchorPane theOverview = (AnchorPane) loader.load();

			// Set book overview into the center of root layout.
			rootLayout.setCenter(theOverview);

			// Give the controller access to the main app.
			BookOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the book overview inside the root layout.
	 */
	public void showCheckoutOverview() {
		try {

			// Load checkout overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CheckoutOverview.fxml"));
			AnchorPane theOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(theOverview);

			// Give the controller access to the main app.
			CheckoutController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog to edit details for the specified user. If the user clicks
	 * OK, the changes are saved into the provided user object and true is
	 * returned.
	 *
	 * @param userInterface
	 *            the user object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showUserEditDialog(UserInterface userInterface, int mode) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/UserEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			if (mode == 1) {
				dialogStage.setTitle("Edit User");

			} else {
				dialogStage.setTitle("Add User");
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			UserEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(userInterface);

			// Set the dialog icon.
			dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showMemberEditDialog(MemberInterface memberInterface, int mode) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MemberEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			if (mode == 1) {
				dialogStage.setTitle("Edit Member");

			} else {
				dialogStage.setTitle("Add Member");
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			MemberEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMember(memberInterface);

			// Set the dialog icon.
			dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showBookEditDialog(BookInterface bookInterface, int mode) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BookEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			if (mode == 1) {
				dialogStage.setTitle("Edit Book");

			} else {
				dialogStage.setTitle("Add Book");
			}
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the book into the controller.
			BookEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBook(bookInterface);

			// Set the dialog icon.
			dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showCheckoutDialog(Book book) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BookCheckoutDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Checkout Book");

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the book into the controller.
			BookCheckoutDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setCheckoutBook(book);

			// Set the dialog icon.
			dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean showCheckinDialog(){
		try{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/CheckInController.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle("CheckIn Book");

		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
     	// Set the dialog icon.
		dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));

		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
CheckInController c=new CheckInController();

		return c.isOkClicked();
		}
	 catch (IOException e) {
		e.printStackTrace();
		return false;
	}


	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}