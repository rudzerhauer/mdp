package net.etfbl.api;

import net.etfbl.model.EmailRequest;

import java.io.File;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/email")
public class APIServiceEmail {
    private EmailService emailService;

    public APIServiceEmail() {
        emailService = new EmailService();
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(EmailRequest emailRequest) {
        if (emailRequest.getRecipient() == null || emailRequest.getSubject() == null || emailRequest.getBody() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required email parameters.").build();
        }
        try {
        boolean success = emailService.sendEmailWithAttachment(
                emailRequest.getRecipient(),
                emailRequest.getSubject(),
                emailRequest.getBody(),
                emailRequest.getFilePaths()
        );

        if (success) {
            return Response.status(Response.Status.OK)
                    .entity("Email sent successfully to " + emailRequest.getRecipient()).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to send email.").build();
        }
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to send email: " + e.getMessage()).build();
        }
    }
}
