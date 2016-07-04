package dataaccess.storage;

import java.util.ArrayList;
import java.util.List;

import library.core.Role;
import library.core.User;

public class staticdata {

	private String UserName;
	private String Password;
	private Role role;

	public static List<User> getUser() throws Exception
	{
		List<User> usr=new ArrayList<User>();
		 //usr.add(new User(1,"nisha",SHA3Util.hashPassword("nisha"),Role.ADMIN));
		 //usr.add(new User(2,"nidina",SHA3Util.hashPassword("nidina"),Role.BOTH));
		 return usr;


	}

}
