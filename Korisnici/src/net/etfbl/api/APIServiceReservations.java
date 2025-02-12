package net.etfbl.api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.model.Reservation;

@Path("/reservations")

public class APIServiceReservations {
	ReservationService rs;
	
	public APIServiceReservations() {
		rs = new ReservationService();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getAll() {
		return rs.getReservations();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		Reservation reservation = rs.getById(id);
		if(reservation!=null) {
			return Response.status(200).entity(reservation).build();
		} else
			return Response.status(404).build();
		
		
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Reservation reservation) {
		if(rs.add(reservation)) {
			return Response.status(200).entity(reservation).build();
		}
		else
			return Response.status(404).build();
	}
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Reservation reservation, @PathParam("id") int id) {
		if(rs.update(reservation, id)) {
			return Response.status(200).entity(reservation).build();
		} else
			return Response.status(404).build();
	}
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id")int id) {
		if(rs.remove(id)) {
			return Response.status(200).build();
		} else
			return Response.status(404).build();
	}
	
	
	
	
}
