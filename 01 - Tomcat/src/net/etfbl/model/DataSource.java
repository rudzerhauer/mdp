package net.etfbl.model;

import java.util.ArrayList;
import net.etfbl.users.*;

public class DataSource {

    public ArrayList<Member> members = new ArrayList<>();
    public ArrayList<Book> books = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();
    private static DataSource instance = null;

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    private DataSource() {
        System.out.println("Initializing demo data...");

        // Učitavanje članova iz fajla
        members = new ArrayList<>(UserFileHandler.loadUsers());
        books = new ArrayList<>(BookFileHandler.loadBooks());
        reservations = new ArrayList<>(ReservationFileHandler.loadReservations());

        // Inicijalizacija knjiga
     

        // Inicijalizacija rezervacija
        
    }

    // Opciono: metode za upravljanje kolekcijama
    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void addMember(Member member) {
        members.add(member);
        // Čuvanje u fajl prilikom dodavanja
        UserFileHandler.saveUsers(members);
    }

    public void addBook(Book book) {
        books.add(book);
        BookFileHandler.saveBooks(books);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        ReservationFileHandler.saveReservations(reservations);
    }

    // Metoda za čuvanje svih članova pri gašenju aplikacije
    public void saveAllMembers() {
        UserFileHandler.saveUsers(members);
    }
    public void saveAllBooks() {
    	BookFileHandler.saveBooks(books);
    }
    public void saveAllReservations() {
    	ReservationFileHandler.saveReservations(reservations);
    }
}
