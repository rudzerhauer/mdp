package net.etfbl.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.etfbl.model.DataSource;
import net.etfbl.model.User;
import net.etfbl.users.UserFileHandler;

public class Service {
	private static int lastId = 0;

	DataSource data = DataSource.getInstance();

	public ArrayList<User> getStudents() {
		return data.members;
	}
	public boolean exists(User member) {
	    System.out.println("Checking credentials for user: " + member.getUsername());

	    for (User s : data.members) {
	        System.out.println("Checking against user: " + s.getUsername());
	        if (s.getUsername().equals(member.getUsername()) && s.getPassword().equals(member.getPassword())) {
	            return true;
	        }
	    }
	    return false;
	}
	public boolean registerUser(User member) {
	    System.out.println("Trying to register user: " + member.getFirstName());
	    for (User m : data.members) {
	        if (m.getUsername().equals(member.getUsername())) {
	            System.out.println("Username already exists");
	            return false;
	        }
	        if (m.getPassword().trim().equals(member.getPassword().trim())) {
	            System.out.println("Password already exists");
	            return false;
	        }
	    }
	    System.out.println("Moze registracija");
	    return true;
	}
	public User getUserByUsernameAndPassword(String username, String password) {
	    // Iterate over all members to find the matching username and password
	    for (User u : data.members) {
	        if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
	            return u;  // Return the user if found
	        }
	    }
	    return null;  // Return null if no matching user is found
	}





	public User getById(int id) {
		for (User s : data.members) {
			if (s.getId() == id) {
				return s;
			}
		}
		return new User();
	}

	public boolean add(User member) {
	    if (member.getId() == null || member.getId() == 0) {
	        // If the ID is null or 0, generate a new ID
	        int id = new Random().nextInt(100);
	        while (id == 0) {
	            id = new Random().nextInt(100);
	        }
	        member.setId(id);
	    }
	    data.saveAllMembers();
	    return data.members.add(member);
	}



	public boolean update(User student, int id) {
	    for (int i = 0; i < data.members.size(); i++) {
	        User currentUser = data.members.get(i);
	        if (currentUser.getId() == id) {
	            // Update the user details in the list
	        	data.saveAllMembers();
	            data.members.set(i, student);
	            return true;
	        }
	    }
	    return false;
	}


	public boolean remove(int id) {
		int index = data.members.indexOf(new User(id));
		if (index >= 0) {
			data.members.remove(index);
			return true;
		} else {
			return false;
		}
	}
	public boolean activateUser(int id) {
	    User user = getById(id);
	    if (user != null && !user.getActive()) {
	        user.setActive(true);
	        data.saveAllMembers();
	        return update(user, id); // Save the changes
	    }
	    return false;
	}
	
	public List<User> getInactiveUsers() {
	    return (List<User>) getStudents().stream()
	            .filter(user -> !user.getActive())
	            .collect(Collectors.toList());
	}

}
