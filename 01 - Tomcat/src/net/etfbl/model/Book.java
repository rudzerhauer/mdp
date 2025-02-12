package net.etfbl.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Book {

    private final Integer id; // Unique identifier
    private String title;
    private String author;
    private int year;
    private boolean available;
    private String filePath; // Path to the book's content file

    // Constructors
    public Book(Integer id, String title, String author, int year, boolean available, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = available;
        this.filePath = filePath;
    }

    public Book(Integer id, String title, String author, int year, boolean available) {
        this(id, title, author, year, available, null);
    }

    public Book() {
        this.id = null;
    }

    public Book(Integer id) {
        this(id, null, null, 0, false, null);
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Method to dynamically load the book's content from file
    private String loadContent() {
        if (filePath == null || filePath.isEmpty()) {
            return "File path not set for this book.";
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error loading content: " + e.getMessage();
        }
        return contentBuilder.toString();
    }
    
    public String getContent() {
    	String content = this.loadContent();
    	return content;
    }

    // Method to return the first 100 lines of the book's content
    public String getPreview() {
        if (filePath == null || filePath.isEmpty()) {
            return "File path not set for this book.";
        }

        StringBuilder previewBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null && lineCount < 100) {
                previewBuilder.append(line).append("\n");
                lineCount++;
            }
        } catch (IOException e) {
            return "Error loading preview: " + e.getMessage();
        }
        return previewBuilder.toString();
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + 
               ", year=" + year + ", available=" + available + 
               ", filePath=" + (filePath != null ? filePath : "N/A") + "]";
    }
}
