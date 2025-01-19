package net.etfbl.main;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.etfbl.users.BookFileHandler;
import net.etfbl.api.BookService;
import net.etfbl.model.*;
import java.util.List;
import net.etfbl.api.*;

public class OpenUserWindow {

    private final List<Book> books;
    private final BookService bookService = new BookService();

    public OpenUserWindow() {
        this.books = bookService.getBooks();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("User Dashboard");

        TableView<Book> tableView = new TableView<>();

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

        // Adding refresh button
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> refreshTable(tableView));

        VBox layout = new VBox(10, tableView, refreshButton);
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();
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

    private void refreshTable(TableView<Book> tableView) {
        List<Book> updatedBooks = BookFileHandler.loadBooks();
        Platform.runLater(() -> tableView.getItems().setAll(updatedBooks));
    }
}
