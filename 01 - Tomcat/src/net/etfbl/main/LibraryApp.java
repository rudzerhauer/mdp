package net.etfbl.main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.etfbl.api.BookService;
import net.etfbl.api.Service;
import net.etfbl.model.*;
import net.etfbl.users.BookFileHandler;
import net.etfbl.users.ReservationFileHandler;
import net.etfbl.users.UserFileHandler;
import net.etfbl.users.*;

public class LibraryApp {
	

    public Scene createMainScene() {
        // Main layout
        BorderPane root = new BorderPane();

        // MenuBar
        MenuBar menuBar = new MenuBar();

        // Menus
        Menu membersMenu = new Menu("Članovi");
        Menu booksMenu = new Menu("Knjige");
        Menu reservationsMenu = new Menu("Rezervacije");

        // Adding menus to MenuBar
        menuBar.getMenus().addAll(membersMenu, booksMenu, reservationsMenu);

        // Setting MenuBar at the top
        root.setTop(menuBar);

        // Main content area
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        // Buttons for actions
        Button addUserButton = new Button("Dodaj Člana");
        Button addBookButton = new Button("Dodaj Knjigu");
        Button addReservationButton = new Button("Dodaj Rezervaciju");

        // Setting button actions
        addUserButton.setOnAction(e -> showAddUserForm(content));
        addBookButton.setOnAction(e -> showAddBookForm(content));
        addReservationButton.setOnAction(e -> showAddReservationForm(content));

        // Adding buttons to content
        content.getChildren().addAll(addUserButton, addBookButton, addReservationButton);

        // Adding actions to menus
        membersMenu.setOnAction(e -> showAddUserForm(content));
        booksMenu.setOnAction(e -> showAddBookForm(content));
        reservationsMenu.setOnAction(e -> showAddReservationForm(content));

        // Setting content in the center
        root.setCenter(content);

        // Main scene
        return new Scene(root, 800, 600);
    }
    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private PasswordField createPasswordField(String promptText) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        return passwordField;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private void showAddUserForm(VBox content) {
        content.getChildren().clear();

        // Create form fields
        TextField firstNameField = createTextField("Ime");
        TextField lastNameField = createTextField("Prezime");
        TextField emailField = createTextField("E-mail");
        TextField usernameField = createTextField("Korisničko ime");
        PasswordField passwordField = createPasswordField("Lozinka");

        // Add User Button
        Button submitButton = new Button("Dodaj Člana");
        submitButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showAlert("Greška", "Sva polja moraju biti popunjena.");
                return;
            }

            User newUser = new User(new Random().nextInt(100), firstName, lastName, email, username, password, false);
            boolean success = UserFileHandler.addUser(newUser);

            if (success) {
                showAlert("Uspjeh", "Član je uspješno dodan.");
            } else {
                showAlert("Greška", "Korisničko ime već postoji.");
            }
        });

        // Display form
        content.getChildren().add(createFormGrid(
                new String[]{"Ime:", "Prezime:", "E-mail:", "Korisničko ime:", "Lozinka:"},
                new Control[]{firstNameField, lastNameField, emailField, usernameField, passwordField, submitButton}
        ));
    }

    private void showAddBookForm(VBox content) {
        content.getChildren().clear();

        // Create form fields
        TextField titleField = createTextField("Naslov");
        TextField authorField = createTextField("Autor");
        TextField yearField = createTextField("Godina");
        CheckBox availableCheckBox = new CheckBox("Dostupno");

        // Add Book Button
        Button submitButton = new Button("Dodaj Knjigu");
        submitButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                boolean available = availableCheckBox.isSelected();

                if (title.isEmpty() || author.isEmpty()) {
                    showAlert("Greška", "Naslov i autor su obavezni.");
                    return;
                }

                Book newBook = new Book(new Random().nextInt(100), title, author, year, available);
                boolean success = BookFileHandler.addBook(newBook);

                if (success) {
                    showAlert("Uspjeh", "Knjiga je uspješno dodana.");
                } else {
                    showAlert("Greška", "Neuspješno dodavanje knjige.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Greška", "Godina mora biti broj.");
            }
        });

        // Display form
        content.getChildren().add(createFormGrid(
                new String[]{"Naslov:", "Autor:", "Godina:", "Dostupno:"},
                new Control[]{titleField, authorField, yearField, availableCheckBox, submitButton}
        ));
    }

    private void showAddReservationForm(VBox content) {
        content.getChildren().clear();

        // Create form fields
        TextField memberIdField = createTextField("ID Člana");
        TextField bookIdField = createTextField("ID Knjige");
        TextField dateField = createTextField("Datum");
        TextField statusField = createTextField("Status");

        // Add Reservation Button
        Button submitButton = new Button("Dodaj Rezervaciju");
        submitButton.setOnAction(e -> {
            try {
                int memberId = Integer.parseInt(memberIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                String date = dateField.getText();
                String status = statusField.getText();

                Service ss = new Service();
                BookService bs = new BookService();
                User member = ss.getById(memberId);
                Book book = bs.getById(bookId);

                if (member == null || book == null) {
                    showAlert("Greška", "Neispravan ID člana ili knjige.");
                    return;
                }

                Reservation newReservation = new Reservation(new Random().nextInt(100), member, book, date, status);
                boolean success = ReservationFileHandler.addReservation(newReservation);

                if (success) {
                    showAlert("Uspjeh", "Rezervacija je uspješno dodana.");
                } else {
                    showAlert("Greška", "Neuspješno dodavanje rezervacije.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Greška", "ID člana i knjige moraju biti brojevi.");
            }
        });

        // Display form
        content.getChildren().add(createFormGrid(
                new String[]{"ID Člana:", "ID Knjige:", "Datum:", "Status:"},
                new Control[]{memberIdField, bookIdField, dateField, statusField, submitButton}
        )); }
    void showLoginForm(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label usernameLabel = new Label("Korisničko ime:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Lozinka:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Prijava");
        Button registerButton = new Button("Registracija");

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(registerButton, 1, 3);

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        registerButton.setOnAction(e -> showRegistrationForm(stage));

        Scene scene = new Scene(grid, 400, 200);
        stage.setTitle("Prijava na sistem");
        stage.setScene(scene);
        stage.show();
    }
    private void showRegistrationForm(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField addressField = new TextField();
        TextField emailField = new TextField();
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button registerButton = new Button("Registruj se");

        grid.add(new Label("Ime:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Prezime:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Korisničko ime:"), 0, 4);
        grid.add(usernameField, 1, 4);
        grid.add(new Label("Lozinka:"), 0, 5);
        grid.add(passwordField, 1, 5);
        grid.add(new Label("Potvrda lozinke:"), 0, 6);
        grid.add(confirmPasswordField, 1, 6);
        grid.add(registerButton, 1, 7);

        registerButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)) {
                showAlert("Greška", "Lozinke se ne poklapaju.");
                return;
            }

            // REST API poziv za registraciju
            try {
                String url = "http://localhost:8080/01/api/studenti/register";
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(
                                "{\"firstName\":\"" + firstName + "\", " +
                                        "\"lastName\":\"" + lastName + "\", " +
                                        "\"address\":\"" + address + "\", " +
                                        "\"email\":\"" + email + "\", " +
                                        "\"username\":\"" + username + "\", " +
                                        "\"password\":\"" + password + "\"}"
                        ))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 201) {
                    showAlert("Uspjeh", "Registracija uspješna!");
                    showLoginForm(stage); // Vraćanje na login
                } else {
                    showAlert("Greška", "Registracija nije uspjela.");
                }
            } catch (Exception ex) {
                showAlert("Greška", "Greška prilikom registracije: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        stage.setTitle("Registracija");
        stage.setScene(scene);
    }


    private void handleLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Greška", "Unesite korisničko ime i lozinku.");
            return;
        }

        try {
            // Create JSON body with credentials
            Gson gson = new Gson();
            String jsonBody = gson.toJson(new User(username, password));

            // REST API endpoint for login
            String url = "http://localhost:8080/01/api/studenti/login";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send request and parse response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body()); // Check if the response contains id and active fields
            if (response.statusCode() == 200) {
                User loggedInUser = gson.fromJson(response.body(), User.class);
                System.out.println("Logged in User: " + loggedInUser);

                // Check if the user profile is active
                if (!loggedInUser.getActive()) {
                    showAlert("Greška", "Vaš profil nije aktivan. Obratite se administraciji.");
                    return;
                }

                // Determine user type and show respective window
                if (username.startsWith("LB_")) {
                    showAlert("Uspjeh", "Dobrodošli, bibliotekaru!");
                    openLibrarianWindow(loggedInUser);
                } else {
                    showAlert("Uspjeh", "Dobrodošli, korisniče!");
                    openUserWindow(loggedInUser);
                }
            } else {
                showAlert("Greška", "Pogrešno korisničko ime ili lozinka.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            showAlert("Greška", "Došlo je do greške prilikom slanja zahteva.");
        }
    }

    // Placeholder methods for opening librarian and user windows
    private void openLibrarianWindow(User librarian) {
        System.out.println("Opening librarian window for: " + librarian.getUsername());
        Service service = new Service();
        LibrarianWindow librarianWindow = new LibrarianWindow();
        librarianWindow.show();
    }

    private void openUserWindow(User user) {
    	 System.out.println("Opening user window for: " + user.getUsername());
         Service service = new Service();
         OpenUserWindow ouw = new OpenUserWindow();
         ouw.show();
    }

    

    
    private GridPane createFormGrid(String[] labels, Control[] controls) {
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        for (int i = 0; i < labels.length; i++) {
            if (i < controls.length) { // Fix: Ensure that controls array matches labels size
                grid.add(new Label(labels[i]), 0, i);
                grid.add(controls[i], 1, i);
            }
        }

        return grid; // Return the constructed GridPane
    }

}
