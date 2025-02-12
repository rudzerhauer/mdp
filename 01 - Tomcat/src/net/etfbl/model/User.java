package net.etfbl.model;

import java.util.Objects;

public class User {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	boolean active;

	public User() {
	}

	public User(int id) {
		this.id = id;
	}

	public User(int id, String firstName, String lastName, String email, String username, String password, boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = active;
	}

	public User(String username2, String password2) {
		this.username = username2;
		this.password = password2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    User user = (User) obj;
	    return id == user.id;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}
	@Override
	public String toString() {
		return "Member " + firstName + " " + lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}
	public boolean getActive() {
		return this.active;
	}
	public void setActive(boolean active) {
	   this.active = active;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}

}
