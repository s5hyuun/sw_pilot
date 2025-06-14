package unit04;

import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public void changeAvailability(boolean status) {
        this.isAvailable = status;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void printInfo() {
        System.out.println(title + ", " + author + ", " + (isAvailable ? "대출 가능" : "대출 불가"));
    }
}

class User {
    protected String userId;
    protected String name;
    protected List<Book> borrowedBooks = new ArrayList<>();

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.changeAvailability(false);
            borrowedBooks.add(book);
            System.out.println("이용자 '" + name + "' '" + book.getTitle() + "' 대출합니다.");
        } else {
            System.out.println("'" + book.getTitle() + "'은 대출 중입니다.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.changeAvailability(true);
            borrowedBooks.remove(book);
            System.out.println("이용자 '" + name + "'가 '" + book.getTitle() + "' 반납합니다.");
        } else {
            System.out.println("이용자가 대출하지 않은 책입니다.");
        }
    }
}

class Manager extends User {
    public Manager(String userId, String name) {
        super(userId, name);
    }

    public void addBook(Library library, Book book) {
        library.getBooks().add(book);
        System.out.println("관리자 '" + name + "'가 책을 추가합니다: " + book.getTitle() + ", " + book.getAuthor());
    }

    public void removeBook(Library library, Book book) {
        if (!book.isAvailable()) {
            System.out.println("대출 중인 책은 삭제할 수 없습니다.");
            return;
        }
        if (library.getBooks().contains(book)) {
            library.getBooks().remove(book);
            System.out.println("책 '" + book.getTitle() + "' 삭제되었습니다.");
        } else {
            System.out.println("등록되지 않은 책입니다.");
        }
    }
}

class Library {
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void registerUser(User user) {
        users.add(user);
        String userType = (user instanceof Manager) ? "관리자" : "이용자";
        System.out.println("새로운 " + userType + " '" + user.name + "'를 등록합니다.");
    }

    public void printAllBooks() {
        for (Book book : books) {
            book.printInfo();
        }
    }

    public void printAllUsers() {
        for (User user : users) {
            System.out.println(user.name + " (ID: " + user.userId + ")");
        }
    }

    public void searchBooksByAuthor(String author) {
        boolean found = false;
        System.out.println("저자 '" + author + "'의 책 목록:");
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                book.printInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과 해당하는 책이 없습니다.");
        }
    }
}

public class RuleOfBodome02 {
    public static void main(String[] args) {
        Library library = new Library();

        Manager manager = new Manager("m001", "세이코");
        User user1 = new User("u001", "메리");
        User user2 = new User("u002", "만옥");

        library.registerUser(manager);
        library.registerUser(user1);
        library.registerUser(user2);

        Book book1 = new Book("자바의 구름", "제임스밥", "001");
        Book book2 = new Book("파이썬 마스터", "한송희", "002");
        Book book3 = new Book("에너지 플로우", "키네틱스", "003");
        Book book4 = new Book("화성에서의 기억", "한송희", "004");
        Book book5 = new Book("야채의 비밀", "송은정", "005");

        manager.addBook(library, book1);
        manager.addBook(library, book2);
        manager.addBook(library, book3);
        manager.addBook(library, book4);
        manager.addBook(library, book5);

        user1.borrowBook(book1);

        Book book6 = new Book("자료구조의 언덕", "황수", "006");
        Book book7 = new Book("그곳에 가면", "한송희", "007");

        manager.addBook(library, book6);
        manager.addBook(library, book7);

        user2.borrowBook(book1);

        user1.returnBook(book1);

        manager.borrowBook(book4);

        library.searchBooksByAuthor("한송희");
    }
}