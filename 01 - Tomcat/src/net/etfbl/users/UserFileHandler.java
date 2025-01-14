package net.etfbl.users;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import net.etfbl.model.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class UserFileHandler {

    private static final String FILE_PATH = "C:\\Users\\nikola\\Desktop\\01\\01 - Tomcat\\resources\\users.json";
    private static final Gson gson = new Gson();
    

    // Čitanje korisnika iz fajla
    public static List<Member> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<Member>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Pisanje korisnika u fajl
    public static void saveUsers(List<Member> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog korisnika
    public static boolean addUser(Member newUser) {
        List<Member> users = loadUsers();
        for (Member user : users) {
            if (user.getUsername().equals(newUser.getUsername())) {
                System.out.println("User with this username already exists: " + newUser.getUsername());
                return false; // Korisnik sa ovim korisničkim imenom već postoji
            }
        }
        users.add(newUser);
        System.out.println("Adding user: " + newUser.getUsername());
        saveUsers(users);
        return true;
    }

}
