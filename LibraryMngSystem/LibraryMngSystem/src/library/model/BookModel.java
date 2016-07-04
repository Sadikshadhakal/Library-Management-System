package library.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import library.General;
import library.core.Author;
import library.core.Book;
import library.core.BookCopy;
import library.dataaccess.DataAccessFacade;

public class BookModel {

	final static int START = 1000;
	final static boolean DEBUG = false;
	final static String fileName = "books.dat";

	public static void main(String[] args) {

		List<Book> list = new LinkedList<Book>();
		list = loadBooks();
		display(list);

		System.out.println();
		displayById(list, 1003);
	}

	public static List<Book> loadBooks() {

		List<Book> list = new LinkedList<Book>();

		File f = null;
		boolean fileExist = false;
		try {
			f = new File(General.OUTPUT_DIR + "//" + BookModel.fileName);

			// tests if file exists
			fileExist = f.exists();

			if (fileExist == true) {

				// Load from the file
				list = BookModel.readBook();
			} else {
				/* File Does not exists */
				// Reset the Books & Save
				BookModel.resetBook(list);
				BookModel.saveBook(list);
				System.out.println(" Books are reset. ");
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return list;
	}

	public static int getNewID(List<Book> list) {
		if (list.size() == 0)
			return START;

		int max = list.get(0).getID();
		for (Book m : list) {
			if (m.getID() >= max) {
				max = m.getID();
			}
		}

		return (max + 1);
	}

	public static int getLastID(List<Book> list) {
		if (list.size() == 0)
			return -1;

		Book tmp = list.get(list.size() - 1);
		return tmp.getID();
	}

	// Find the index from the linked list
	public static int findIndex(List<Book> list, int id) {

		int findIndex = -1; // default
		int counter = 0;
		for (Book c : list) {
			if (id == c.getID()) {
				// MATCH FOUND by ID
				findIndex = counter;
				break;
			}
			counter++;
		}

		return findIndex;
	}

	public static void display(List<Book> list) {
		// Display each item
		for (Book c : list) {
			c.display();
		}
	}

	// READ
	public static void displayById(List<Book> list, int id) {

		boolean found = false;
		// Display each
		for (Book c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				found = true;
				c.display();
				break;
			}
		}

		if (!found)
			System.out.println(" No Book is found.");
	}

	// Return book object by ID
	public static Book getBookById(List<Book> list, int id) {
		Book tmpBook = null;

		for (Book c : list) {
			if (id == c.getID()) {
				// MATCH FOUND
				tmpBook = c;
				break;
			}
		}

		return tmpBook;
	}

	// DELETE the Book of the given ID
	/*
	 *
	 */

	// UPDATE the Book of the given ID
	public static boolean updateBook(List<Book> list, int id, Book newData) {
		// 1. First check if the id is present or not
		int index = findIndex(list, id);

		if (index == -1) {
			return false;
		} else {
			Book temp = list.set(index, newData);

			if (DEBUG) {
				System.out.println(" Book is updated!");
				temp.display();
			}

			if (temp.getID() >= START) {
				return true;
			} else {
				return false;
			}

		}
	}



public static List<Author> GetlistAuthor()
{

	List<Author> lstauthor=new ArrayList<Author>();
	Author auth1 = new Author(1,"Jenish", "Karanjit", "9848484848", "1000 N Street", "Fairfield", "IA", "52557",
			"Karanjit@gmail.com", "Jenish Karanjit is a very talented author");
	Author auth2 = new Author(2,"Bob", "Hero", "9848484848", "1002 N Street", "Fairfield", "IA", "52557",
			"bobHero@gmail.com", "Bob Hero is good writer");
	Author auth3 = new Author(3,"Harry", "Peter", "9848484848", "1003 N Street", "Fairfield", "IA", "52557",
			"Happy@gmail.com", "Peter is a nice guy");
	Author auth4 = new Author(4,"Green", "Belt", "9848484848", "1004 N Street", "Fairfax", "VA", "52557",
			"Green@gmail.com", "Dan is an excellent author");

	lstauthor.add(auth1);
	lstauthor.add(auth2);
	lstauthor.add(auth3);
	lstauthor.add(auth4);
	return lstauthor;

}




	public static void resetBook(List<Book> list) {

		list.clear();

		// Add Book to the list along with Authors and BookCopy
		Author auth1 = new Author(1,"Jenish", "Karanjit", "9848484848", "1000 N Street", "Fairfield", "IA", "52557",
				"Karanjit@gmail.com", "Jenish Karanjit is a very talented author");
		Author auth2 = new Author(2,"Bob", "Hero", "9848484848", "1002 N Street", "Fairfield", "IA", "52557",
				"bobHero@gmail.com", "Bob Hero is good writer");
		Author auth3 = new Author(3,"Harry", "Peter", "9848484848", "1003 N Street", "Fairfield", "IA", "52557",
				"Happy@gmail.com", "Peter is a nice guy");
		Author auth4 = new Author(4,"Green", "Belt", "9848484848", "1004 N Street", "Fairfax", "VA", "52557",
				"Green@gmail.com", "Dan is an excellent author");

		Book book1 = new Book(BookModel.getNewID(list), "9848484848", "Introduction to Java", 21);
		book1.addAuthor(auth3);

		BookCopy bc, bc2, bc3;
		bc = new BookCopy(Book.getUniqueBookID());
		bc.setBook(book1);
		bc2 = new BookCopy(Book.getUniqueBookID());
		bc2.setBook(book1);
		book1.addBookCopy(bc);
		book1.addBookCopy(bc2);
		list.add(book1); // <----------------------

		Book book2 = new Book(BookModel.getNewID(list), "9848484848", "LLLLJava", 21);
		book2.addAuthor(auth4);

		bc = new BookCopy(Book.getUniqueBookID());
		bc.setBook(book2);
		bc2 = new BookCopy(Book.getUniqueBookID());
		bc2.setBook(book2);
		bc3 = new BookCopy(Book.getUniqueBookID());
		bc2.setBook(book2);
		book2.addBookCopy(bc);
		book2.addBookCopy(bc2);
		book2.addBookCopy(bc3);
		list.add(book2); // <----------------------

		Book book3 = new Book(BookModel.getNewID(list), "9786123409123", "Professional Programming Practice", 7);
		book3.addAuthor(auth1);
		book3.addAuthor(auth2);

		bc = new BookCopy(Book.getUniqueBookID());
		bc.setBook(book3);
		book3.addBookCopy(bc);
		list.add(book3); // <----------------------

		Book book4 = new Book(BookModel.getNewID(list), "9785120934", "Object Oriented Programming", 21);
		book4.addAuthor(auth1);

		bc = new BookCopy(Book.getUniqueBookID());
		bc.setBook(book4);
		book4.addBookCopy(bc);
		list.add(book4); // <----------------------

		Book book5 = new Book(BookModel.getNewID(list), "9786123823145", "Algorithm", 21);
		book5.addAuthor(auth2);
		book5.addAuthor(auth3);

		bc = new BookCopy(Book.getUniqueBookID());
		bc.setBook(book5);
		bc2 = new BookCopy(Book.getUniqueBookID());
		bc2.setBook(book5);
		bc3 = new BookCopy(Book.getUniqueBookID());
		bc2.setBook(book5);
		book5.addBookCopy(bc);
		book5.addBookCopy(bc2);
		book5.addBookCopy(bc3);
		list.add(book5); // <----------------------
	}
public static int getmaxlength(List<Book> list, int id)
{
	for (Book c : list) {
		if (id == c.getID()) {
			// MATCH FOUND
			return c.getMaxCheckoutLength();
		}
	}
	return 0;

}

	// SAVE TO THE FILE
	public static void saveBook(List<Book> list) {

		// 1. First Sort it
		Collections.sort(list);

		// 2. Now Save it the file
		DataAccessFacade dataAccess = new DataAccessFacade();
		dataAccess.saveBook(fileName, list);
	}

	// READ FROM THE FILE
	public static List<Book> readBook() {

		// Make Book RW object
		DataAccessFacade dataAccess = new DataAccessFacade();
		return (dataAccess.readBook(fileName));
	}

}
