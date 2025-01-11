package net.etfbl.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.model.Member;

@Path("/studenti")
public class APIService {

	StudentService service;

	public APIService() {
		service = new StudentService();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Member> getAll() {
		return service.getStudents();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		Member student = service.getById(id);
		if (student != null) {
			return Response.status(200).entity(student).build();
		} else {
			return Response.status(404).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Member student) {
		if (service.add(student)) {
			return Response.status(200).entity(student).build();
		} else {
			return Response.status(500).entity("Greska pri dodavanju").build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Member student, @PathParam("id") int id) {
		if (service.update(student, id)) {
			return Response.status(200).entity(student).build();
		}
		return Response.status(404).build();
	}

	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") int id) {
		if (service.remove(id)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
}