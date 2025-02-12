package net.etfbl.api;
import java.util.*;

import net.etfbl.model.DataSource;
import net.etfbl.model.Reservation;

public class ReservationService {
	DataSource data = DataSource.getInstance();
	
	
	public ArrayList<Reservation> getReservations() {
		return data.reservations;
	}
	
	public Reservation getById(int id) {
		for(Reservation r:data.reservations) {
			if(r.getId() == id) {
				return r;
			}
		}
		return new Reservation();
	}
	public boolean add(Reservation reservation) {
		reservation.setId(new Random().nextInt(100));
		return data.reservations.add(reservation);
	}
	
	public boolean update(Reservation reservation, int id) {
		int index = data.reservations.indexOf(new Reservation(id));
		if(index >= 0) {
			data.reservations.set(index, reservation);
			return true;
		} else {
			return false;
		}
		
	}
	public boolean remove(int id) {
		int index = data.reservations.indexOf(new Reservation(id));
		if(index >= 0) {
			data.reservations.remove(index);
			return true;
		} else {
			return false;
		}
		 
		
	}
	
	
}
