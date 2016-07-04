package library.dataaccess;

import java.util.List;

import library.core.Book;
import library.core.CheckOutRecord;
import library.core.Member;
import library.core.User;

public interface DataAccess {
	// 1. for Member
	public void saveMember(String name, List<Member> members);
	public List<Member> readMember(String name);

	// 2. for User
	public void saveUser(String name, List<User> users);
	public List<User> readUser(String name);

	// 3. for Book
	public void saveBook(String name, List<Book> books);
	public List<Book> readBook(String name);

	// 4. for CheckOutRecords
	public void saveCheckOut(String name, List<CheckOutRecord> checkouts);
	public List<CheckOutRecord> readCheckout(String name);
}
