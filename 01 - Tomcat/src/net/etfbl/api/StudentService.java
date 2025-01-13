package net.etfbl.api;

import java.util.ArrayList;
import java.util.Random;

import net.etfbl.model.DataSource;
import net.etfbl.model.Member;

public class StudentService {

	DataSource data = DataSource.getInstance();

	public ArrayList<Member> getStudents() {
		return data.members;
	}
	public boolean exists(Member member) {
	    System.out.println("Checking credentials for user: " + member.getUsername());

	    for (Member s : data.members) {
	        System.out.println("Checking against user: " + s.getUsername());
	        if (s.getUsername().equals(member.getUsername()) && s.getPassword().equals(member.getPassword())) {
	            return true;
	        }
	    }
	    return false;
	}



	public Member getById(int id) {
		for (Member s : data.members) {
			if (s.getId() == id) {
				return s;
			}
		}
		return new Member();
	}

	public boolean add(Member member) {
		member.setId(new Random().nextInt(100));
		return data.members.add(member);
	}

	public boolean update(Member student, int id) {
		int index = data.members.indexOf(new Member(id));
		if (index >= 0) {
			data.members.set(index, student);
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(int id) {
		int index = data.members.indexOf(new Member(id));
		if (index >= 0) {
			data.members.remove(index);
			return true;
		} else {
			return false;
		}
	}
}
