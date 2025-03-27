package service;

import model.Book;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.UserValidation;
import utils.MyList;

public class MainServiceImpl implements MainService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private User activeUser;

    public MainServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String email, String password) {

        if (!UserValidation.isEmailValid(email)) {
            System.out.println("\n    Емейл не прошел проверку!");
            return null;
        }

        if (!UserValidation.isPasswordValid(password)) {
            System.out.println("\n    Пароль не прошел проверку!");
            return null;
        }

        if (userRepository.isEmailExist(email)) {
            System.out.println("\n    Пользователь уже есть, так как email уже существует!");
            return null;
        }
        return userRepository.addUser(email, password);
    }

    @Override
    public boolean loginUser(String email, String password) {

        User user = userRepository.getUserByEmail(email);
        if (user == null) return false; // Если пользователя нет — false

        if (user.getPassword().equals(password)) {
            activeUser = user;
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        activeUser = null;
    }


    @Override
    public void addBook(String title, String author) {
        bookRepository.addBook(title, author);
    }

    @Override
    public MyList<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public MyList<Book> getBooksByTitle(String title) {
        return null; // Заглушка
    }

    @Override
    public MyList<Book> getAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    @Override
    public MyList<Book> getTakenBooks() {
        return bookRepository.getTakenBooks();
    }

    @Override
    public void deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public void takeBook(int id) {
        bookRepository.takeBook(id);

    }

    @Override
    public void returnBook(int id) {
        bookRepository.returnBook(id);
    }

    @Override
    public void editBook(int id, String newTitle, String newAuthor) {
        bookRepository.editBook(id, newTitle, newAuthor);
    }

    @Override
    public void logoutUser() {
        // Заглушка
    }

    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public MyList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public MyList<Book> searchByTitle(String title) {
        return null;
    }

    @Override
    public MyList<Book> searchByAuthor(String author) {
        return null;
    }

    @Override
    public MyList<Book> searchByTitleOrAuthor(String query) {
        return bookRepository.searchByTitleOrAuthor(query);
    }

    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean deleteUser(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        if (!user.getUserBooks().isEmpty()) {
            return false;
        }
        return userRepository.deleteUser(email);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        if (!UserValidation.isPasswordValid(newPassword)) {
            return false;
        }
        return userRepository.updatePassword(email, newPassword);
    }
    @Override
    public MyList<Book> getSortedBooks(String sortField) {
        return bookRepository.getSortedBooks(sortField);
    }
}