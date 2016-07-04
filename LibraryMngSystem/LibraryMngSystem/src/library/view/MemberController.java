package library.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.swing.text.AbstractDocument.Content;



import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import library.MainApp;
import library.core.Member;
import library.model.BookModel;
import library.model.MemberInterface;
import library.model.MemberModel;

public class MemberController {

	private List<Member> list = new LinkedList<Member>(); // important

	@FXML
	private TableView<MemberInterface> theTable;
	@FXML
	private TableColumn<MemberInterface, String> nameColumn;
	@FXML
	private TableColumn<MemberInterface, Number> memberId;

	@FXML
	private Label idLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label zipLabel;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public MemberController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {





		list = MemberModel.loadMembers();

		// Initialize the member table with the two columns

		nameColumn.setCellValueFactory(cellData -> (cellData.getValue().NameProperty()));
		memberId.setCellValueFactory(cellData -> cellData.getValue().userIDProperty());

		// Clear member details.
		showMemberDetails(null);

		// Listen for selection changes and show the member details when
		// changed.
		theTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMemberDetails(newValue));
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
	private void showMemberDetails(MemberInterface MemberInterface) {
		if (MemberInterface != null) {
			// Fill the labels with info from the member object.
			System.out.println(MemberInterface);

			idLabel.setText(Integer.toString(MemberInterface.getMemberID()));
			firstNameLabel.setText(MemberInterface.getfirstName());
			lastNameLabel.setText(MemberInterface.getlastName());
			phoneLabel.setText(MemberInterface.getPhone());
			streetLabel.setText(MemberInterface.getStreet());
			cityLabel.setText(MemberInterface.getCity());
			stateLabel.setText(MemberInterface.getState());
			zipLabel.setText(MemberInterface.getZip());

		} else {
			idLabel.setText("");
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			phoneLabel.setText("");
			streetLabel.setText("");
			cityLabel.setText("");
			stateLabel.setText("");
			zipLabel.setText("");
		}
	}

	/**
	 * Called when the member clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewMember() {

		MemberInterface tempMember = new MemberInterface();

		// get the new MemberID ------
		tempMember.setMemberID(MemberModel.getNewID(list));

		/* 0 for Edit */
		boolean okClicked = mainApp.showMemberEditDialog(tempMember, 0);

		if (okClicked) {
			mainApp.getMemberData().add(tempMember);

			Member m=new Member(MemberModel.getNewID(list), tempMember.getfirstName(), tempMember.getlastName(),
					tempMember.getPhone(), tempMember.getStreet(), tempMember.getCity(), tempMember.getState(),
					tempMember.getZip());
			m.setName(tempMember.getfirstName()+" "+tempMember.getlastName());
			list.add(m);

			// ----- FINALLY SAVE TO THE FILE -----
			MemberModel.saveMember(list);
			MemberModel.display(list);
		}
	}

	/**
	 * Called when the member clicks the edit button. Opens a dialog to edit
	 * details for the selected member.
	 */
	@FXML
	private void handleEditMember() {
		MemberInterface selected = theTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			boolean okClicked = mainApp.showMemberEditDialog(selected,
					1); /* 1 for Edit */

			if (okClicked) {
				showMemberDetails(selected);

				// add to the our LinkedList Also

				boolean editCheck = false;
				int editID = selected.getMemberID();
				editCheck = MemberModel.updateMember(list, editID,
						new Member(editID, selected.getfirstName(), selected.getlastName(), selected.getPhone(),
								selected.getStreet(), selected.getCity(), selected.getState(), selected.getZip()));
				System.out.println(editCheck);
				MemberModel.saveMember(list);
				MemberModel.display(list);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Member Selected");
			alert.setContentText("Please select a member in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the member clicks on the delete button.
	 */

	@FXML
	private boolean Confirm()
	{


		Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("Confirmation");
	      alert.setHeaderText("Are you sure you want to delete?" + "\n" );
	      Optional<ButtonType> result = alert.showAndWait();
	      //result.get() == ButtonType.OK
	      if (result.get() == ButtonType.OK) {
	        return true;
	      }
	      else
	      {
	    	  return false;
	      }

	}
	@FXML
	private void handleDeleteMember() {



	int selectedIndex = theTable.getSelectionModel().getSelectedIndex();
	if (selectedIndex >= 0) {
		boolean decision=Confirm();
		if(decision==true)
		{


		if (theTable.getItems().size() < 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Delete Member");
			alert.setHeaderText("Delete Member");
			alert.setContentText("If you delete this user there will be no user to login system");

			alert.showAndWait();
			return;
		}

		MemberInterface deleteMember = theTable.getSelectionModel().getSelectedItem();
		int deleteIndex = deleteMember.getMemberID();
		System.out.println(deleteIndex);

		// REMOVE from THE FILE ALSO ------ ------ ------
		boolean deleteCheck = false;
		deleteCheck = MemberModel.deleteMember(list, deleteIndex);
		System.out.println(deleteCheck);

		if (deleteCheck) {
			// Remove from the list temporary
			theTable.getItems().remove(selectedIndex);
			System.out.println(selectedIndex);

			MemberModel.saveMember(list);
			MemberModel.display(list);
		}
		}



	} else {
		// Nothing selected.
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("No Selection");
		alert.setHeaderText("No Member Selected");
		alert.setContentText("Please select a member in the table.");

		alert.showAndWait();
	}





	}
}