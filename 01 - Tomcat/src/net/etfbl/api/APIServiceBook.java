package net.etfbl.api;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.model.*;
import java.util.ArrayList;

import javax.ws.rs.*;

@Path("/books")
public class APIServiceBook {

	BookService bookService;
	
	public APIServiceBook() {
		bookService = new BookService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) 
		public ArrayList<Book> getAll() {
			return bookService.getBooks();
		}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		Book book = bookService.getById(id);
		if(book != null) {
			return Response.status(200).entity(book).build();
		} else {
			return Response.status(404).build();
		}
		}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Book book) {
		if(bookService.add(book)) {
			return Response.status(200).entity(book).build();
		} else 
		return Response.status(404).build();
	}
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) 
	public Response edit(Book book, @PathParam("id") int id) {
		if(bookService.update(book, id) ) {
			return Response.status(200).entity(book).build();
		}
		return Response.status(404).build();
	}
	}
		
	

