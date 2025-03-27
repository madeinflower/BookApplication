package service;

import model.Book;
import model.User;
import utils.MyList;


public interface MainService {

    User registerUser(String email, String password);

    boolean loginUser(String email, String password);

    void logout();

    void addBook(String title, String author);

    MyList<Book> getAllBooks();

    MyList<Book> getBooksByTitle(String title);

    MyList<Book> getAvailableBooks();

    void deleteBook(int bookId);

    MyList<Book> getTakenBooks();

    void takeBook(int id);

    void returnBook(int id);

    void editBook(int id, String newTitle, String newAuthor);

    //void logoutUser();

    void logoutUser();

    User getActiveUser();

    MyList<User> getAllUsers();

    MyList<Book> searchByTitle(String title);

    MyList<Book> searchByAuthor(String author);

    MyList<Book> searchByTitleOrAuthor(String titleSearch);
    User getUserByEmail(String email);

    boolean deleteUser(String email);

    boolean updatePassword(String email, String newPassword);

    MyList<Book> getSortedBooks(String sortField);

}