package net.etfbl.main;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.etfbl.api.APIService;
import net.etfbl.model.User;


import java.util.List;
import net.etfbl.api.*;

public class LibrarianWindow {

    private final List<User> inactiveUsers;
    Service service = new Service();

    public LibrarianWindow() {
        this.inactiveUsers = service.getInactiveUsers();
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Librarian Dashboard");

        TableView<User> tableView = new TableView<>();

        // Definisanje kolona
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button activateButton = new Button("Activate");

            {
                activateButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    activateUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(activateButton);
                }
            }
        });

        // Dodavanje kolona u tabelu
        tableView.getColumns().addAll(usernameColumn, emailColumn, actionColumn);
        tableView.getItems().addAll(inactiveUsers);

        // Dodavanje dugmeta za osvežavanje
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> refreshTable(tableView));

        VBox layout = new VBox(10, tableView, refreshButton);
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void activateUser(User user) {
        boolean success = service.activateUser(user.getId());
        if (success) {
            Platform.runLater(() -> inactiveUsers.remove(user));
            user.setActive(true);
            service.update(user, user.getId());// Uklanjanje korisnika iz lokalne liste
        } else {
            System.out.println("Failed to activate user: " + user.getUsername());
        }
    }

    private void refreshTable(TableView<User> tableView) {
        List<User> updatedInactiveUsers = service.getInactiveUsers();
        tableView.getItems().setAll(updatedInactiveUsers);
    }
}
