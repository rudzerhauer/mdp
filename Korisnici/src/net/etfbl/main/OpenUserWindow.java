package net.etfbl.main;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.etfbl.users.BookFileHandler;
import net.etfbl.api.BookService;
import net.etfbl.model.*;
import net.etfbl.api.*;


import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.google.gson.Gson;

import java.util.ArrayList;

public class OpenUserWindow {

    private final List<Book> books;
    private final BookService bookService = new BookService();
    private EmailService es = new EmailService();

    public OpenUserWindow() {
        this.books = bookService.getBooks();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("User Dashboard");

        TableView<Book> tableView = new TableView<>();

        // Enable multiple selection
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Defining columns
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        TableColumn<Book, Void> detailsColumn = new TableColumn<>("Details");
        detailsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");

            {
                detailsButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    showDetailsPopup(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                }
            }
        });

        // Adding columns to the table
        tableView.getColumns().addAll(titleColumn, authorColumn, detailsColumn);
        tableView.getItems().addAll(books);

        // Adding "Send as ZIP" button
        Button zipButton = new Button("Send Books as ZIP");
        zipButton.setOnAction(event -> sendBooksAsZip(tableView.getSelectionModel().getSelectedItems()));

        VBox layout = new VBox(10, tableView, zipButton);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void sendBooksAsZip(List<Book> selectedBooks) {
        if (selectedBooks.isEmpty()) {
            showAlert("Error", "Please select at least one book.");
            return;
        }

        // Create a list of file paths for the selected books
        List<String> filePaths = new ArrayList<>();
        for (Book book : selectedBooks) {
            filePaths.add(book.getFilePath()); // Assuming getFilePath() returns the path to the book file
        }

        try {
            // Prepare the email request with the ZIP file paths
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setRecipient("nikola.gataric01@gmail.com");  // Set the recipient dynamically
            emailRequest.setSubject("Books Download");
            emailRequest.setBody("Here are the books you requested.");
            emailRequest.setFilePaths(filePaths);  // Set the list of file paths

            // Convert the EmailRequest object to JSON
            Gson gson = new Gson();
            String jsonBody = gson.toJson(emailRequest);

            // Send the request to the API
            String url = "http://localhost:8080/01/api/email/send";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response
            if (response.statusCode() == 200) {
                System.out.println("Email sent successfully: " + response.body());
            } else {
                System.out.println("Failed to send email. Response: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while sending the email.");
        }
    }


   
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showDetailsPopup(Book book) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Book Details");

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        Label titleLabel = new Label("Title: " + book.getTitle());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        TextArea previewText = new TextArea(book.getPreview()); // Assuming Book has a getPreview() method
        previewText.setEditable(false);

        layout.getChildren().addAll(titleLabel, authorLabel, previewText);
        Scene scene = new Scene(layout, 400, 300);
        detailsStage.setScene(scene);
        detailsStage.show();
    }
}
