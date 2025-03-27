package repository;

import model.Book;
import utils.MyList;

public interface BookRepository {

    void addBook(String title, String author);

    MyList<Book> getAllBooks();

    MyList<Book> searchByTitle(String query);
    MyList<Book> searchByAuthor(String query);
    MyList<Book> searchByTitleOrAuthor(String query);

    MyList<Book> getAvailableBooks();
    MyList<Book> getTakenBooks();
    MyList<Book> getSortedBooks(String sortField);

    void takeBook(int id);
    void returnBook(int id);
    void deleteById(int id);
    void editBook(int id, String newTitle, String newAuthor);
}
