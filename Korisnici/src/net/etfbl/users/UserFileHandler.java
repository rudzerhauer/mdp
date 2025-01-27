package net.etfbl.users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import net.etfbl.model.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserFileHandler {

    private static final String FILE_PATH = "C:\\Users\\nikola\\Desktop\\01\\01 - Tomcat\\resources\\users.json";
    private static final Gson gson = new Gson();
    

    // Čitanje korisnika iz fajla
    public static List<User> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, userListType);
            if (users == null) users = new ArrayList<>();

            // Ensure that all users have a valid ID and active field is correctly set
            for (User user : users) {
                if (user.getId() == null || user.getId() == 0) {
                    user.setId(new Random().nextInt(100)); // Assign random ID if missing or 0
                }
                System.out.println("User loaded: " + user.getUsername() + " | Active: " + user.getActive());
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    // Pisanje korisnika u fajl
    public static void saveUsers(List<User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog korisnika
    public static boolean addUser(User newUser) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                System.out.println("User with this username already exists: " + newUser.getUsername());
                return false; // Korisnik sa ovim korisničkim imenom već postoji
            }
        }
        newUser.setId(new Random().nextInt(100));
        users.add(newUser);
        System.out.println("Adding user: " + newUser.getUsername());
        saveUsers(users);
        return true;
    }

}
