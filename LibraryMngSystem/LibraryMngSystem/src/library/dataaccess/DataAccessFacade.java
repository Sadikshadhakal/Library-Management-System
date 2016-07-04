package library.dataaccess;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import library.core.Book;
import library.core.CheckOutRecord;
import library.core.Member;
import library.core.User;

public class DataAccessFacade implements DataAccess {
	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	@Override
	public void saveMember(String name, List<Member> members) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));

			for (Member m : members) {
				out.writeObject(m);
			}

		} catch (IOException e) {
			System.err.println(" Error opening file.");
			// e.printStackTrace();
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println(" Invalid input.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
	}

	public List<Member> readMember(String name) {
		List<Member> list = new ArrayList<>();
		ObjectInputStream in = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			in = new ObjectInputStream(Files.newInputStream(path));

			// member = (Member) in.readObject();
			while (true) {
				Member m = (Member) in.readObject();
				list.add(m);
			}
		} catch (EOFException eofException) {
			return list;
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println(" Object creation failed.");
		} catch (IOException ioException) {
			System.err.println(" Error opening file.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
		return list;
	}

	// For the User ---------------------------------------------------------------------
	@Override
	public void saveUser(String name, List<User> users) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));

			for (User u : users) {
				out.writeObject(u);
			}

		} catch (IOException e) {
			System.err.println(" Error opening file.");
			// e.printStackTrace();
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println(" Invalid input.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
	}

	public List<User> readUser(String name) {
		List<User> list = new ArrayList<>();
		ObjectInputStream in = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			in = new ObjectInputStream(Files.newInputStream(path));

			while (true) {
				User u = (User) in.readObject();
				list.add(u);
			}
		} catch (EOFException eofException) {
			return list;
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println(" Object creation failed.");
		} catch (IOException ioException) {
			System.err.println(" Error opening file.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
		return list;
	}

	// For the Book ---------------------------------------------------------------------
	@Override
	public void saveBook(String name, List<Book> books) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			out = new ObjectOutputStream(Files.newOutputStream(path));

			for (Book b : books) {
				out.writeObject(b);
			}

		} catch (IOException e) {
			System.err.println(" Error opening file.");
			// e.printStackTrace();
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println(" Invalid input.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
	}

	public List<Book> readBook(String name) {
		List<Book> list = new ArrayList<>();
		ObjectInputStream in = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
			in = new ObjectInputStream(Files.newInputStream(path));

			while (true) {
				Book b = (Book) in.readObject();
				list.add(b);
			}
		} catch (EOFException eofException) {
			return list;
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println(" Object creation failed.");
		} catch (IOException ioException) {
			System.err.println(" Error opening file.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					System.err.println(" Error closing file.");
				}
			}
		}
		return list;
	}

	// For the Checkout ---------------------------------------------------------------------
		@Override
		public void saveCheckOut(String name, List<CheckOutRecord> checkouts) {
			ObjectOutputStream out = null;
			try {
				Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
				out = new ObjectOutputStream(Files.newOutputStream(path));

				for (CheckOutRecord c : checkouts) {
					out.writeObject(c);
				}

			} catch (IOException e) {
				System.err.println(" Error opening file.");
				// e.printStackTrace();
			} catch (NoSuchElementException noSuchElementException) {
				System.err.println(" Invalid input.");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (Exception e) {
						System.err.println(" Error closing file.");
					}
				}
			}
		}

		public List<CheckOutRecord> readCheckout(String name) {
			List<CheckOutRecord> list = new ArrayList<>();
			ObjectInputStream in = null;
			try {
				Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, name);
				in = new ObjectInputStream(Files.newInputStream(path));

				while (true) {
					CheckOutRecord c = (CheckOutRecord) in.readObject();
					list.add(c);
				}
			} catch (EOFException eofException) {
				return list;
			} catch (ClassNotFoundException classNotFoundException) {
				System.err.println(" Object creation failed.");
			} catch (IOException ioException) {
				System.err.println(" Error opening file.");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						System.err.println(" Error closing file.");
					}
				}
			}
			return list;
		}

}