package repository;

import model.User;
import model.Role;
import utils.MyArrayList;
import utils.MyList;


public class UserRepositoryImpl implements UserRepository {

    private final MyList<User> users;

    public UserRepositoryImpl() {
        users = new MyArrayList<>();
        addUsers();
    }

    private void addUsers() {
        User admin = new User("admin@admin.com", "Password123!");
        admin.setRole(Role.ADMIN);

        User user = new User("user@user.com", "Password123!");
        user.setRole(Role.USER);

        User user1 = new User("user1@library.com", "pass1");
        user1.setRole(Role.USER);

        User user2 = new User("user2@library.com", "pass2");
        user2.setRole(Role.USER);

        User user3 = new User("user3@library.com", "pass3");
        user3.setRole(Role.USER);

        users.addAll(admin, user, user1, user2, user3);
    }

    @Override
    public MyList<User> getAllUsers() {
        return users;
    }

    @Override
    public User addUser(String email, String password) {
        User user = new User(email, password);
        users.add(user);
        return user;
    }

    @Override
    public boolean isEmailExist(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                user.setPassword(newPassword);
                //System.out.println("Пароль успешно изменён!");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(String email) {

        User userToDelete = getUserByEmail(email);
        if (userToDelete == null) {
            return false;
        }
        return users.remove(userToDelete);
    }
}
