package model;

import java.util.LinkedList;
import java.util.List;

public class MUFRUserDatabase {
	
	private List<MUFRUser> users;
	
	public MUFRUserDatabase()
	{
		users = new LinkedList<MUFRUser>();
	}

	public List<MUFRUser> getUsers() {
		return users;
	}

	public void setUsers(List<MUFRUser> users) {
		this.users = users;
	}
	
	public MUFRUser getUserByID(Long long1)
	{
		MUFRUser returnUser = null;
		
		for(MUFRUser user: users)
		{
			if(user.getId() == long1)
				return user;
		}
		
		return returnUser;
	}
}
