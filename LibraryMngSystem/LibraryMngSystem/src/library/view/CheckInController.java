package library.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import library.core.Book;
import library.core.BookCopy;
import library.core.BookMember;
import library.core.CheckInRecord;
import library.core.CheckOutRecord;
import library.core.Member;
import library.model.BookModel;
import library.model.CheckoutInterface;
import library.model.CheckoutModel;
import library.model.MemberInterface;
import library.model.MemberModel;



public class CheckInController {
	private List<CheckOutRecord> list = new LinkedList<CheckOutRecord>();
	private List<Book> listbook = new LinkedList<Book>();


	@FXML
	private TextField bookIdField;

	@FXML
	private TextField bookIdField1;

	@FXML
	private Label bookid;
	@FXML
	private DatePicker returndateField;
	@FXML
	ListView listView;


	private Member member;
	private Book book;

	private Stage dialogStage;
	// private CheckoutInterface checkoutInterface;
	private boolean okClicked = false;

	private  List<CheckOutRecord> filterList = new LinkedList<CheckOutRecord>();

	//private List<CheckOutRecord> clist = new LinkedList<CheckOutRecord>();

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		member = null;
		list = CheckoutModel.loadCheckouts();
		listbook = BookModel.loadBooks();
		//LocalDate today = LocalDate.now();
		//returndateField.setValue(today);
//		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent click) {
//
//				if (click.getClickCount() == 2) {
//					String currentItemSelected = (String) listView.getSelectionModel().getSelectedItem();
//
//					System.out.println(currentItemSelected);
//				}
//			}
//		});



				// Listen for selection changes and show the member details when
				// changed.

			}
	@FXML
	public void handleMouseClick(MouseEvent arg0) {
	    String abc=listView.getSelectionModel().getSelectedItem().toString();
	    String abc2=abc.substring(abc.length()-5);
	    System.out.println(abc2);
	    System.out.println("dsdasdas"+abc2.substring(0,abc2.length()-1));
	    bookIdField1.setText(abc2.substring(0,abc2.length()-1));


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
	 * Sets the book variable
	 *
	 * @param book
	 */


	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
public void deleteCheckOut(List<CheckOutRecord> list, long id){
	list.remove(id);
	CheckoutModel.saveCheckOut(list);
	CheckoutModel.display(list);

}

@FXML
private void findlist()
{
	ObservableList<String> selectedIndexes = FXCollections.observableArrayList();

	List<BookMember> bm = CheckoutModel.getrecordByMemberId(list,Integer.parseInt(bookIdField.getText()));
	for (int i = 0; i<bm.size(); i++) {
		//selectedIndexes.add("raj " + i);
		selectedIndexes.add(bm.toString());
	}
	listView.setItems(selectedIndexes);

}






@FXML
public void deleteCheckOut(List<CheckOutRecord> lst,int memid, int bookId)
{
	int count=0;
	List<CheckOutRecord> duplicate=new LinkedList<CheckOutRecord>();
	for(CheckOutRecord ce:lst)
	{
		if(ce.getBookcopy().getBook().getID()!=bookId && ce.getMember().getID()!=memid)
		{
			filterList.add(ce);
		}

		if(ce.getBookcopy().getBook().getID()==bookId && ce.getMember().getID()==memid)
		{
			count++;
			duplicate.add(ce);
		}
	}
	for(int i=0;i<count-1;i++)
	{
		filterList.add(duplicate.get(i));
	}
CheckoutModel.saveCheckOut(filterList);

	}



	/**
	 * Called when the user clicks OK.
	 */
	@FXML
	private void handleCheckIn() {


		if (isInputValid()) {
			double fine= 0.0;
			CheckInRecord checkin = null;
			try {


				List<Book> lstbk=BookModel.readBook();
				 Book oldBook = BookModel.getBookById(lstbk, Integer.parseInt(bookIdField1.getText()));

	  List<BookCopy> lstbkcpy=oldBook.getCopies();
	  for(BookCopy b:lstbkcpy)

	  {
		  if(b.isAvailable()==false)
		  {
			  b.setAvailable(true);
			  break;
		  }


	  }




		boolean editCheck = BookModel.updateBook(lstbk, oldBook.getID(), oldBook);
		System.out.println(editCheck);
		System.out.println(editCheck);
		BookModel.saveBook(lstbk);
		BookModel.display(lstbk);







				CheckOutRecord cr = CheckoutModel.getCheckOutById(list,Integer.parseInt(bookIdField1.getText()));

				 cr.getBookcopy().setAvailable(true);
				deleteCheckOut(list, Integer.parseInt(bookIdField.getText()),Integer.parseInt(bookIdField1.getText()));

				int maxlength = BookModel.getmaxlength(listbook,Integer.parseInt(bookIdField1.getText()));
				LocalDate today = LocalDate.now();
				LocalDate previousDate= cr.getDueDate();
		Long range=ChronoUnit.DAYS.between(today,previousDate);


		if(maxlength== 7 && range >7)
		{
			//fine is $2
			fine = (range-7)* 2;
			bookid.setText("Since you delayed to return .You are fined with $ "+ fine);
		}
		else if(maxlength== 21 && range >21)
		{
			//fine is $2
			fine = (range-21)* 2;
			bookid.setText("Since you delayed to return .You are fined with $ "+ fine);
		}
		else{
			bookid.setText("Thank you for returning book in time.");
		}

	this.okClicked=true;

		} catch (NullPointerException nep) {
				nep.getMessage();
				okClicked = false;
			}
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
		System.out.println("this is book Id fied"+bookIdField.getText());



		if (bookIdField.getText() == null || bookIdField.getText().length() == 0) {
			errorMessage += "No valid Book ID code!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				System.out.println(Integer.parseInt(bookIdField.getText()));
			} catch (NumberFormatException e) {
				errorMessage += "Invalid Book ID (must be an integer)!\n";
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
