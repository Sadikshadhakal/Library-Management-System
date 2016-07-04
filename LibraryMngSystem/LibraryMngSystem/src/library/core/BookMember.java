package library.core;

public class BookMember {

	private String name;
	private int id;

	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name+"          "+id ;
	}
	public BookMember(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	//generate getters and setter



}
