package repository;

import model.Book;
import utils.MyArrayList;
import utils.MyList;
import java.util.concurrent.atomic.AtomicInteger;
import static view.RainbowConsole.prnt;


public class BookRepositoryImpl implements BookRepository {

    private final MyList<Book> books;
    private final AtomicInteger currentId = new AtomicInteger(1);

    public BookRepositoryImpl() {
        this.books = new MyArrayList<>();
        addStartBooks();
    }

    private void addStartBooks() {
        books.addAll(
                new Book(currentId.getAndIncrement(), "Чистый код. Создание, анализ и рефакторинг", "Роберт Мартин"),
                new Book(currentId.getAndIncrement(), "Совершенный программист. Путь к мастерству", "Эндрю Хант, Дэвид Томас"),
                new Book(currentId.getAndIncrement(), "Паттерны проектирования. Решения для повторяющихся проблем", "Эрих Гамма, Ричард Хелм"),
                new Book(currentId.getAndIncrement(), "Python. Краткий курс", "Эрик Мэттес"),
                new Book(currentId.getAndIncrement(), "Вы не знаете JS", "Кайл Симпсон"),
                new Book(currentId.getAndIncrement(), "Эффективный JavaScript", "Дэвид Херман"),
                new Book(currentId.getAndIncrement(), "Fullstack React", "Энтони Аккомадзо, Натан Мюррей"),
                new Book(currentId.getAndIncrement(), "Node.js. Шаблоны проектирования", "Марио Каччаро"),
                new Book(currentId.getAndIncrement(), "Изучаем React", "Алекс Бэнкс, Ив Порцелло"),
                new Book(currentId.getAndIncrement(), "Python. Краткий курс", "Эрик Мэттес"),
                new Book(currentId.getAndIncrement(), "Head First. Паттерны проектирования", "Эрик Фриман, Элизабет Робсон"));
    }

    @Override
    public void addBook(String title, String author) {
        Book book = new Book(currentId.getAndIncrement(), title, author);
        books.add(book);
    }

    @Override
    public MyList<Book> getAllBooks() {
        return books;
    }

    public MyList<Book> searchByTitle(String title) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public MyList<Book> searchByAuthor(String author) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public MyList<Book> searchByTitleOrAuthor(String query) {
        MyList<Book> result = new MyArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }


    @Override
    public MyList<Book> getAvailableBooks() {
        MyList<Book> availableBooks = new MyArrayList<>();
        for (Book book : books) {
            if (!book.isTaken()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    public MyList<Book> getTakenBooks() {
        MyList<Book> takenBooks = new MyArrayList<>();
        for (Book book : books) {
            if (book.isTaken()) {
                takenBooks.add(book);
            }
        }
        return takenBooks;
    }


    @Override
    public void takeBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) book.setTaken(true);
        }
    }


    @Override
    public void returnBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) book.setTaken(false);
        }
    }


    @Override
    public void editBook(int id, String newTitle, String newAuthor) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);

            }
        }
    }

    @Override
    public void deleteById(int id) {
        for (Book book : books) {
            if (book.getId() == id) books.remove(book);
        }
    }

    @Override
    public MyList<Book> getSortedBooks(String sortField) {

        MyList<Book> allBooks = getAllBooks();
        if (allBooks == null || allBooks.isEmpty()) {
            return allBooks;
        }

        Book[] booksArray = allBooks.toArray();

        for (int i = 0; i < booksArray.length - 1; i++) {
            for (int j = i + 1; j < booksArray.length; j++) {
                String fieldI = "";
                String fieldJ = "";
                if ("author".equalsIgnoreCase(sortField)) {
                    fieldI = booksArray[i].getAuthor().toLowerCase();
                    fieldJ = booksArray[j].getAuthor().toLowerCase();
                } else if ("title".equalsIgnoreCase(sortField)) {
                    fieldI = booksArray[i].getTitle().toLowerCase();
                    fieldJ = booksArray[j].getTitle().toLowerCase();
                }
                if (fieldI.compareTo(fieldJ) > 0) {
                    Book temp = booksArray[i];
                    booksArray[i] = booksArray[j];
                    booksArray[j] = temp;
                }
            }
        }

        MyList<Book> sortedBooks = new MyArrayList<>();
        for (Book book : booksArray) {
            sortedBooks.add(book);
        }
        return sortedBooks;
    }
}