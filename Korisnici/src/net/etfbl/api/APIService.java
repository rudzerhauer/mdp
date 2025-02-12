package net.etfbl.api;

import java.util.ArrayList;
import java.util.List;

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
import net.etfbl.users.*;
import net.etfbl.model.User;

@Path("/studenti")
public class APIService {

	Service service;

	public APIService() {
		service = new Service();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAll() {
		return service.getStudents();
	}
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User newMember) {
	    System.out.println("Received registration request: " + newMember.getUsername());

	    boolean isValid = service.registerUser(newMember);
	    System.out.println("Validation result: " + isValid);

	    boolean isAdded = false;
	    if (isValid) {
	        isAdded = UserFileHandler.addUser(newMember);
	    }
	    System.out.println("Add result: " + isAdded);

	    if (isValid && isAdded) {
	        return Response.status(201).entity(newMember).build();
	    } else {
	        return Response.status(500).entity("Error during registration").build();
	    }
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User student) {
	    User foundUser = service.getUserByUsernameAndPassword(student.getUsername(), student.getPassword());
	    if (foundUser != null) {
	        System.out.println("Returning user: " + foundUser.getId()); // Debugging log
	        return Response.status(200).entity(foundUser).build();
	    } else {
	        return Response.status(404).entity("User not found or invalid credentials").build();
	    }
	}


	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		User student = service.getById(id);
		if (student != null) {
			return Response.status(200).entity(student).build();
		} else {
			return Response.status(404).build();
		}
	}
	 @PUT
	    @Path("/activateUser/{id}")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response activateUser(@PathParam("id") int id) {
	        boolean isActivated = service.activateUser(id);
	        if (isActivated) {
	            return Response.status(200).entity("User activated successfully").build();
	        }
	        return Response.status(400).entity("Failed to activate user").build();
	    }
	
	 @GET
	    @Path("/inactiveUsers")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response getInactiveUsers() {
	        List<User> inactiveUsers = service.getInactiveUsers();
	        if (inactiveUsers.isEmpty()) {
	            return Response.status(204).build(); // No Content
	        }
	        return Response.status(200).entity(inactiveUsers).build();
	    }
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(User student) {
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
	public Response edit(User student, @PathParam("id") int id) {
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
