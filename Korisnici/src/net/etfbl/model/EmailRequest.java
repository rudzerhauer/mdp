package net.etfbl.model;

import java.util.List;

public class EmailRequest {
    private String recipient;
    private String subject;
    private String body;
    private List<String> filePaths;

    // Getters and setters
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public void setFilePaths(List<String> filePaths) {
    	this.filePaths = filePaths;
    }
    public List<String> getFilePaths() {
    	return this.filePaths;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
