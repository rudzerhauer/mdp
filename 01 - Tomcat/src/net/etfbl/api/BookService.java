package net.etfbl.api;

import java.util.ArrayList;
import java.util.Random;

import net.etfbl.model.DataSource;
import net.etfbl.model.Book;

public class BookService {

    DataSource data = DataSource.getInstance();

    public ArrayList<Book> getBooks() {
        return data.books;
    }

    public Book getById(int id) {
        for (Book b : data.books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return new Book();
    }

    public boolean add(Book book) {
        
        return data.books.add(book);
    }

    public boolean update(Book book, int id) {
        int index = data.books.indexOf(new Book(id));
        if (index >= 0) {
            data.books.set(index, book);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(int id) {
        int index = data.books.indexOf(new Book(id));
        if (index >= 0) {
            data.books.remove(index);
            return true;
        } else {
            return false;
        }
    }
}
