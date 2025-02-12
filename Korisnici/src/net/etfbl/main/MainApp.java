package net.etfbl.main;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.etfbl.main.LibraryApp;
import net.etfbl.model.User;
import net.etfbl.users.UserFileHandler;
import net.etfbl.api.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
    	LibraryApp la = new LibraryApp();
        la.showLoginForm(primaryStage);
    }

    

	public static void main(String[] args) {
		String baseDirectory = System.getProperty("user.dir");
		System.out.println(baseDirectory);
		Service service = new Service();
		List<User> lista = new ArrayList<>();
		lista = UserFileHandler.loadUsers();
		for(User u:lista) {
			if(!u.getActive() && !u.getUsername().startsWith("LB_")) {
				System.out.println(u);
			}
		}
        launch(args);
        List<User> lista2 = new ArrayList<>();
		lista2 = UserFileHandler.loadUsers();
		for(User u:lista2) {
			if(!u.getActive() && !u.getUsername().startsWith("LB_")) {
				System.out.println(u);
			}
		}
    }
}
