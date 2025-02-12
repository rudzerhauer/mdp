package net.etfbl.users;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import net.etfbl.model.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookFileHandler {

    private static final String FILE_PATH = "C:\\Users\\nikola\\Desktop\\01\\01 - Tomcat\\resources\\books.json";
    private static final Gson gson = new Gson();

    // Čitanje knjiga iz fajla
    public static List<Book> loadBooks() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {}.getType();
            return gson.fromJson(reader, bookListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Pisanje knjiga u fajl
    public static void saveBooks(List<Book> books) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove knjige
    public static boolean addBook(Book newBook) {
        List<Book> books = loadBooks();
        for (Book book : books) {
            if (book.getId() == newBook.getId()) {
                return false; // Knjiga sa ovim ID već postoji
            }
        }
        books.add(newBook);
        saveBooks(books);
        return true;
    }
}
