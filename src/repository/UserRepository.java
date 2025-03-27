package repository;

import model.Book;
import model.User;
import utils.MyList;

public interface UserRepository {

    MyList<User> getAllUsers();
    User addUser(String email, String password);

    boolean isEmailExist(String email);

    User getUserByEmail(String email);

    boolean updatePassword(String email, String newPassword);
    boolean deleteUser(String email);
}
