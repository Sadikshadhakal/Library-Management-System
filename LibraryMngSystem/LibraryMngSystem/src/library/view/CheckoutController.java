package library.view;

import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import library.MainApp;
import library.core.CheckOutRecord;
import library.core.Member;
import library.model.CheckoutModel;
import library.model.MemberInterface;
import library.model.MemberModel;

public class CheckoutController {

	private List<Member> mlist = new LinkedList<Member>(); // for members
	private List<CheckOutRecord> clist = new LinkedList<CheckOutRecord>(); // important

	@FXML
	private TableView<MemberInterface> theTable;
	@FXML
	private TableColumn<MemberInterface, String> firstNameColumn;
	@FXML
	private TableColumn<MemberInterface, String> lastNameColumn;

	@FXML
	private TextArea history;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public CheckoutController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		mlist = MemberModel.loadMembers();
		clist = CheckoutModel.loadCheckouts();


		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		//  display all history
		String output = "";
		for (CheckOutRecord c : clist) {
			output = output + c.display2() + "\n";
		}
		history.setText(output);

		theTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCheckoutDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		theTable.setItems(mainApp.getMemberData());
	}

	/**
	 * Fills all text fields to show details about the member. If the specified
	 * member is null, all text fields are cleared.
	 *
	 * @param MemberInterface
	 *            the member or null
	 */
	private void showCheckoutDetails(MemberInterface memberInterface) {
		if (memberInterface != null) {

			// history.setText(Integer.toString(memberInterface.getMemberID()));
			String output = "";
			for (CheckOutRecord c : clist) {

				// Filter by memberID
				if (c.getMember().getID() == memberInterface.getMemberID()) {
					output = output + c.display2() + "\n";
				}
			}
			history.setText(output);

		} else {
			history.setText("");

		}
	}

	@FXML
	private void print(){
		for(CheckOutRecord cr: clist)
		{
			System.out.println(cr.toString());
		}
	}




	/**
	 * Called when the member clicks on the export button.
	 */
	@FXML
	private void handleExport() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Checkout History");
		alert.setHeaderText("Checkout History Export");
		alert.setContentText("Export feature will be added soon.");

		alert.showAndWait();
	}
}