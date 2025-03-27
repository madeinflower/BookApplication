package view;

import model.Book;
import model.Role;
import model.User;
import service.MainService;
import utils.MyArrayList;
import utils.MyList;

import java.util.Scanner;

import static view.RainbowConsole.*;

public class Menu {

    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(MainService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            User activeUser = service.getActiveUser();
            Role role = (activeUser != null) ? activeUser.getRole() : Role.GUEST;
            showMenu(role, activeUser);

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера ввода
                handleMenuChoice(choice, role);
            } else {
                prnt("\n   [!] Некорректный ввод. Пожалуйста, введите число из списка меню!", 4);
                scanner.nextLine();
                waitRead();
            }
        }
    }

    private void showMenu(Role role, User activeUser) {
        prnt("== 📚 Библиотека \"Знания Века\" ===", 1);
        prnt("1. Все книги", 0);
        prnt("2. Доступные книги", 0);
        prnt("3. Книги, отсортированные по автору", 0);
        prnt("4. Книги, отсортированные по названию", 0);
        prnt("5. Поиск по названию", 0);
        prnt("6. Поиск по автору", 0);

        if (role == Role.USER || role == Role.ADMIN) {
            prnt("-----------------------------", 1);
            prnt("7. Взять книгу", 0);
            prnt("8. Вернуть книгу", 0);
            prnt("9. " + ACCENT + "❤" + RESET + " Мои взятые книги", 0);
        }

        if (role == Role.ADMIN) {
            prnt("--------------------------------", 1);
            prnt("10. Добавить книгу", 1);
            prnt("11. Редактировать книгу", 1);
            prnt("12. Удалить книгу", 1);
            prnt("13. Книги на руках", 1);
            prnt("14. Управление пользователями", 2);
        }

        if (role == Role.GUEST) {
            prnt("15. Регистрация", 2);
            prnt("16. Авторизация", 2);
        }

        if (role == Role.USER || role == Role.ADMIN) {
            String username = activeUser.getEmail();
            String roleName = activeUser.getRole().name();
            prnt("--------------------------------", 1);
            prnt("17. Выйти (" + username + ")", 3);
        }

        prnt("0. Exit", 3);
        prnt("==============================", 1);
        System.out.print("  👉 Выберите пункт меню: ");
    }

    private void handleMenuChoice(int choice, Role role) {
        if (choice == 1) {
            prnt("\n   = Список всех книг библиотеки ===", 1);
            MyList<Book> books = service.getAllBooks();
            if (books == null || books.isEmpty()) {
                prnt("В нашей библиотеке книг пока нет.", 3);
            } else {
                for (Book book : books) {
                    prnt(""
                            + WARNING + book.getId() + ". "+ RESET
                            + "" + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 2) {
            prnt("\n   = Список доступных книг ===", 1);
            MyList<Book> availableBooks = service.getAvailableBooks();
            if (availableBooks == null || availableBooks.isEmpty()) {
                prnt("Доступных книг пока нет.", 3);
            } else {
                for (Book book : availableBooks) {
                    prnt(""
                            + WARNING + book.getId() + ". "+ RESET
                            + "" + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();
        } else if (choice == 3) {
            prnt("\n   = Список книг, отсортированных по автору ===", 1);
            MyList<Book> sortedBooks = service.getSortedBooks("author");
            if (sortedBooks == null || sortedBooks.isEmpty()) {
                prnt("Книг пока нет.", 3);
            } else {
                for (Book book : sortedBooks) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 4) {
            prnt("\n   = Список книг, отсортированных по названию ===", 1);
            MyList<Book> sortedBooks = service.getSortedBooks("title");
            if (sortedBooks == null || sortedBooks.isEmpty()) {
                prnt("Книг пока нет.", 3);
            } else {
                for (Book book : sortedBooks) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 5) {
            prnt("   = Поиск книг по названию ===", 1);
            System.out.print("    Введите название книги или ее часть: ");
            String titleSearch = scanner.nextLine();

            MyList<Book> booksByTitleSearch = service.searchByTitleOrAuthor(titleSearch);

            if (booksByTitleSearch == null) {
                booksByTitleSearch = new MyArrayList<>();
            }

            if (booksByTitleSearch.isEmpty()) {
                prnt("   Книги с названием, содержащими '" + titleSearch + "', не найдены.", 3);
            } else {
                System.out.println();
                for (Book book : booksByTitleSearch) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 6) {
            prnt("\n   = Поиск книг по автору ===", 1);
            System.out.print("    Введите автора книги: ");
            String authorSearch = scanner.nextLine();
            MyList<Book> booksByAuthorSearch = service.searchByTitleOrAuthor(authorSearch);

            if (booksByAuthorSearch == null || booksByAuthorSearch.isEmpty()) {
                prnt("   Книги с автором '" + authorSearch + "' не найдены.", 3);
            } else {
                System.out.println();
                for (Book book : booksByAuthorSearch) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 7 && (role == Role.USER || role == Role.ADMIN)) {

            prnt("\n   = Взять книгу ===\n", 1);

            MyList<Book> availableBooks = service.getAvailableBooks();
            if (availableBooks == null || availableBooks.isEmpty()) {
                prnt("Доступных книг пока нет.", 3);
            } else {
                for (Book book : availableBooks) {
                    prnt(""
                            + WARNING + book.getId() + ". "+ RESET
                            + "" + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }

            System.out.print("\n"+PRIMARY+"   Введите id книги: "+RESET);
            int id = scanner.nextInt();
            scanner.nextLine();

            MyList<Book> allBooks = service.getAllBooks();
            Book selectedBook = null;
            for (Book book : allBooks) {
                if (book.getId() == id) {
                    selectedBook = book;
                    break;
                }
            }

            if (selectedBook == null) {
                prnt("\n   Книга с таким id не найдена.", 4);
            } else if (selectedBook.isTaken()) {
                prnt("\n   Простите, но эта книга уже взята.", 4);
            } else {
                service.takeBook(id);
                service.getActiveUser().getUserBooks().add(selectedBook);
                prnt("\n   Книга \""+ selectedBook.getTitle()+"\" Вами успешно взята!", 2);
            }
            waitRead();

        } else if (choice == 8 && (role == Role.USER || role == Role.ADMIN)) {

            prnt("\n   = Список книг для возврата ===", 1);
            User activeUser = service.getActiveUser();
            MyList<Book> myBooks = activeUser.getUserBooks();
            if (myBooks == null || myBooks.isEmpty()) {
                prnt(" У вас нет взятых книг.", 3);
            } else {
                for (Book book : myBooks) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }

                System.out.print("\n    Введите id книги для возврата: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                Book selectedBook = null;
                MyList<Book> userBooks = activeUser.getUserBooks();
                for (Book book : userBooks) {
                    if (book.getId() == id) {
                        selectedBook = book;
                        break;
                    }
                }

                if (selectedBook == null) {
                    prnt("\n     У вас нет книги с таким id.", 4);
                } else {
                    service.returnBook(id);
                    userBooks.remove(selectedBook);
                    prnt("\n    Книга успешно возвращена!", 2);
                }
            }
            waitRead();

        } else if (choice == 9 && (role == Role.USER || role == Role.ADMIN)) {

            prnt("\n   = Мои взятые книги ===", 1);
            User activeUser = service.getActiveUser();
            MyList<Book> myBooks = activeUser.getUserBooks();
            if (myBooks == null || myBooks.isEmpty()) {
                prnt(" У вас нет взятых книг.", 3);
            } else {
                for (Book book : myBooks) {
                    prnt(WARNING + book.getId() + ". " + RESET
                            + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }
            waitRead();

        } else if (choice == 10 && role == Role.ADMIN) {

            prnt("\n  = Добавление книги в библиотеку ===", 1);

            System.out.print("    Введите название книги: ");
            String title = scanner.nextLine().trim();

            System.out.print("    Введите автора книги: ");
            String author = scanner.nextLine().trim();

            if (title.isEmpty() || author.isEmpty()) {
                prnt("\n Ошибка: название и автор не могут быть пустыми.", 4);
            } else {
                service.addBook(title, author);
                prnt("\n    Книга успешно добавлена!", 2);
            }
            waitRead();

        } else if (choice == 11 && role == Role.ADMIN) {
            prnt("\n   = Редактирование книги ===", 1);

            MyList<Book> availableBooks = service.getAvailableBooks();
            if (availableBooks == null || availableBooks.isEmpty()) {
                prnt("Доступных книг пока нет.", 3);
            } else {
                for (Book book : availableBooks) {
                    prnt(""
                            + WARNING + book.getId() + ". "+ RESET
                            + "" + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }

            System.out.print("\n"+PRIMARY+"   Введите id книги: "+RESET);
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("\n   Введите новое название книги: ");
            String newTitle = scanner.nextLine().trim();

            System.out.print("    Введите нового автора книги: ");
            String newAuthor = scanner.nextLine().trim();

            if (newTitle.isEmpty() || newAuthor.isEmpty()) {
                prnt("\n    Ошибка: название и автор не могут быть пустыми.", 4);
            } else {
                service.editBook(id, newTitle, newAuthor);
                prnt("\n   Книга успешно отредактирована!", 2);
            }
            waitRead();

        } else if (choice == 12 && role == Role.ADMIN) {
            prnt("\n   = Удаление книги из библиотеки ===", 1);
            MyList<Book> availableBooks = service.getAvailableBooks();
            if (availableBooks == null || availableBooks.isEmpty()) {
                prnt("Доступных книг пока нет.", 3);
            } else {
                for (Book book : availableBooks) {
                    prnt(""
                            + WARNING + book.getId() + ". "+ RESET
                            + "" + book.getTitle() + " " + WARNING
                            + "Автор: " + RESET + book.getAuthor(), 3);
                }
            }

            System.out.print("\n"+PRIMARY+"   Введите id книги: "+RESET);
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                scanner.nextLine();
                if (id <= 0) {
                    prnt("\n   Ошибка: указан неверный номер книги.", 4);
                } else {

                    MyList<Book> allBooks = service.getAllBooks();
                    Book selectedBook = null;
                    for (Book book : allBooks) {
                        if (book.getId() == id) {
                            selectedBook = book;
                            break;
                        }
                    }

                    service.deleteBook(id);
                    prnt("\n   Книга "+ selectedBook.getTitle() + " успешно удалена!", 2);
                }
            } else {
                prnt("\n   Ошибка: введите корректное число.", 4);
                scanner.nextLine(); // очистка
            }
            waitRead();

        } else if (choice == 13 && role == Role.ADMIN) {
            prnt("\n   = Книги на руках ===", 1);
            MyList<User> users = service.getAllUsers();
            boolean found = false;
            for (User user : users) {
                MyList<Book> userBooks = user.getUserBooks();
                if (userBooks != null && !userBooks.isEmpty()) {
                    found = true;
                    for (Book book : userBooks) {
                        prnt(WARNING + book.getId() + ". " + RESET
                                + book.getTitle() + " " + WARNING
                                + "Автор: " + RESET + book.getAuthor()
                                + PRIMARY + "  - " + ACCENT + " у читателя: " + PRIMARY + user.getEmail() + RESET + " (xx дн.)", 3);
                    }
                }
            }
            if (!found) {
                prnt("\n    Книг на руках пока нет.", 3);
            }
            waitRead();

        } else if (choice == 14 && role == Role.ADMIN) {
            showUserManagementMenu();
        } else if (choice == 15) {
            addUser("Регистрация");
        } else if (choice == 16) {
            loginUser();
        } else if (choice == 17 && (role == Role.USER || role == Role.ADMIN)) {
            service.logout();
            prnt("\n   Вы вышли из аккаунта!", 2);
            waitRead();
        } else if (choice == 0) {
            prnt("\n   До свидания! Приходите еще!", 2);
            System.exit(0);
        } else {
            prnt("\n   Некорректный ввод. Попробуйте снова.", 4);
            waitRead();
        }
    }

    private void showUserManagementMenu() {
        while (true) {
            prnt("=== Управление пользователями ===", 1);
            prnt("1. Просмотреть список пользователей", 2);
            prnt("2. Найти пользователя и его книги", 2);
            prnt("3. Добавить пользователя", 2);
            prnt("4. Изменить данные пользователя", 2);
            prnt("5. Удалить пользователя", 2);
            prnt("0. Назад", 3);
            prnt("==============================", 1);
            System.out.print("  👉 Выберите пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера ввода

            if (choice == 1) {
                MyList<User> users = service.getAllUsers();

                if (users.isEmpty()) {
                    prnt("\n Список пользователей пуст.", 3);
                } else {
                    prnt("\n = Список пользователей ===\n", 1);
                    int i=0;
                    for (User user : users) {
                        i++;
                        prnt(""+i+": "
                                + "Email: " + user.getEmail() + ", "
                                + "Роль: " + user.getRole() + ", "
                                + "Книги: " + user.getUserBooks().toString(), 3);
                    }
                }
                waitRead();

            } else if (choice == 0) {
                break;
            } else if (choice == 2) {
                prnt("\n   = Поиск пользователя ===", 1);
                System.out.print("     Введите email: ");

                String email = scanner.nextLine();
                User userByEmail = service.getUserByEmail(email);

                if (userByEmail == null) {
                    prnt("\n   Пользователь с email " + email + " не найден.", 3);
                } else {
                    prnt("  Найден пользователь: "
                            + "Email: " + userByEmail.getEmail() + ", "
                            + "Роль: " + userByEmail.getRole() + ", "
                            + "Книги: " + userByEmail.getUserBooks().toString(), 3);
                }
                waitRead();
            } else if (choice == 3){
                addUser("Добавление");
            } else if (choice == 4){
                editUser();
            } else if (choice == 5) {
                deleteUser();
                waitRead();
            }
            else {
                prnt("\n   Некорректный ввод. Попробуйте снова.", 4);
                waitRead();
            }
        }
    }

    private void waitRead() {
        System.out.println("\n   Для продолжения нажмите Enter...");
        scanner.nextLine();
    }

    private void loginUser() {
        prnt("\n   = Авторизация пользователя ===", 1);
        System.out.print("     Введите email: ");
        String email = scanner.nextLine();

        System.out.print("     Введите пароль: ");
        String password = scanner.nextLine();

        boolean loggedIn = service.loginUser(email, password);

        if (loggedIn) {
            prnt("\n   [+] Добро пожаловать, "+ email + "!\n", 2);
        } else {
            prnt("\n   [!] Неверный email или пароль!", 4);
            waitRead();
        }
    }

    private void addUser(String action) {

        prnt("\n   = " + action + " пользователя ===", 1);
        System.out.print("     Введите email: ");
        String email = scanner.nextLine();
        System.out.print("     Введите пароль: ");
        String password = scanner.nextLine();

        User user = service.registerUser(email, password);

        if (user == null) {
            prnt("  [!] Операция отменена", 4);
        } else {
            prnt("\n    [+] Пользователь зарегистрирован", 2);
        }

        waitRead();
    }
    private void deleteUser() {
        prnt("\n = Удаление пользователя ===", 1);
        System.out.print("   Введите его email: ");
        String email = scanner.nextLine();

        if (!service.deleteUser(email)) {
            if (service.getUserByEmail(email) == null) {
                prnt("\n  Пользователь не найден.", 4);
            } else {
                prnt("\n   Невозможно удалить пользователя, так как у него есть книги!", 4);
            }
        } else {
            prnt("\n   Удаление пользователя прошло успешно.", 2);
        }
    }

    private void editUser() {
        prnt("\n = Изменение данных пользователя ===", 1);
        System.out.print("   Введите email пользователя: ");
        String email = scanner.nextLine();

        User user = service.getUserByEmail(email);
        if (user == null) {
            prnt(" Пользователь не найден.", 4);
            waitRead();
            return;
        }

        System.out.print("   Введите новый пароль: ");
        String newPassword = scanner.nextLine();

        boolean result = service.updatePassword(email, newPassword);
        if (result) {
            prnt("\n  Изменение данных пользователя прошло успешно.", 2);
        } else {
            prnt("\n  Не пройдена валидация! Изменение данных отменено.", 4);
        }

        waitRead();
    }
}