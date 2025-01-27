package net.etfbl.users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import net.etfbl.model.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileHandler {

    private static final String FILE_PATH = "C:\\Users\\nikola\\Desktop\\01\\01 - Tomcat\\resources\\reservations.json";
    private static final Gson gson = new Gson();

    // Čitanje rezervacija iz fajla
    public static List<Reservation> loadReservations() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type reservationListType = new TypeToken<ArrayList<Reservation>>() {}.getType();
            return gson.fromJson(reader, reservationListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Pisanje rezervacija u fajl
    public static void saveReservations(List<Reservation> reservations) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(reservations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove rezervacije
    public static boolean addReservation(Reservation newReservation) {
        List<Reservation> reservations = loadReservations();
        reservations.add(newReservation);
        saveReservations(reservations);
        return true;
    }
}
