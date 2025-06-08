package unit04;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RuleOfBiodoem08 {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 도서 객체 생성
        System.out.println("(도서 객체 생성)");
        Book b1 = new Book("파이썬 마스터", "한송희", "2020-01-01");
        Book b2 = new Book("자바의 구름", "제임스밥", "2018-05-05");
        Book b3 = new Book("에너지 플로우", "키네틱스", "2019-08-15");
        Book b4 = new Book("화성에서의 기억", "한송희", "2021-03-03");
        Book b5 = new Book("야채의 비밀", "송은정", "2017-10-10");

        // 도서관 시스템 생성 및 등록
        System.out.println("\n(도서관 시스템 생성 및 등록)");
        Library lib = new Library();
        lib.addBook(b1);
        lib.addBook(b2);
        lib.addBook(b3);
        lib.addBook(b4);
        lib.addBook(b5);

        // 대출 및 반납
        System.out.println("\n(대출1)");
        lib.borrowBook("야채의 비밀");

        System.out.println("\n(대출2)");
        lib.borrowBook("화성에서의 기억");

        System.out.println("\n(반납)");
        lib.returnBook("야채의 비밀");

        // 전체 도서 조회 (제목 순 기본 정렬)
        System.out.println("\n(도서 조회 결과)");
        lib.printBooksSortedByTitle();

        // 저자 기반 정렬
        System.out.println("\n(저자 기반 정렬 조회)");
        lib.printBooksSortedByAuthor();

        // 출판일 기반 정렬
        System.out.println("\n(출판일 기반 정렬 조회)");
        lib.printBooksSortedByPublishedDate();
    }
}

// 도서 클래스
class Book implements Comparable<Book> {
    String title;
    String author;
    LocalDate publishedDate;
    boolean isAvailable;
    LocalDate lastBorrowedDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Book(String title, String author, String publishedDateStr) {
        this.title = title;
        this.author = author;
        this.publishedDate = LocalDate.parse(publishedDateStr, formatter);
        this.isAvailable = true;
        this.lastBorrowedDate = null;
        printInfo();
    }

    public void printInfo() {
        System.out.println("제목: \"" + title + "\", 저자: \"" + author + "\", 출판일: \"" +
                publishedDate + "\", 대출 가능 여부: \"" + (isAvailable ? "가능" : "불가능") +
                "\", 최근 대출 날짜: \"" + (lastBorrowedDate == null ? "N/A" : lastBorrowedDate) + "\"");
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }
}

// Comparator 구현들
class AuthorComparator implements Comparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.author.compareTo(b2.author);
    }
}

class PublishedDateComparator implements Comparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.publishedDate.compareTo(b2.publishedDate);
    }
}

class LastBorrowedDateComparator implements Comparator<Book> {
    public int compare(Book b1, Book b2) {
        if (b1.lastBorrowedDate == null && b2.lastBorrowedDate == null) return 0;
        if (b1.lastBorrowedDate == null) return 1;
        if (b2.lastBorrowedDate == null) return -1;
        return b2.lastBorrowedDate.compareTo(b1.lastBorrowedDate); // 최신 순
    }
}

// 도서관 클래스
class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book b) {
        books.add(b);
        System.out.println("\"" + b.title + "\"가 도서 목록에 추가되었습니다.");
    }

    public void borrowBook(String title) {
        Book book = findBook(title);
        if (book == null) {
            System.out.println("[" + title + "]는 도서 목록에 존재하지 않습니다.");
        } else if (!book.isAvailable) {
            System.out.println("\"" + title + "\"는 현재 대출 중입니다.");
        } else {
            book.isAvailable = false;
            book.lastBorrowedDate = LocalDate.now();
            System.out.println("\"" + title + "\" 대출되었습니다. 최근 대출 날짜 업데이트: " + book.lastBorrowedDate);
        }
    }

    public void returnBook(String title) {
        Book book = findBook(title);
        if (book == null) {
            System.out.println("[" + title + "]는 도서 목록에 존재하지 않습니다.");
        } else if (book.isAvailable) {
            System.out.println("\"" + title + "\"는 이미 반납된 상태입니다.");
        } else {
            book.isAvailable = true;
            System.out.println("\"" + title + "\" 반납되었습니다.");
        }
    }

    public Book findBook(String title) {
        for (Book b : books) {
            if (b.title.equals(title)) return b;
        }
        return null;
    }

    public void printBooksSortedByTitle() {
        List<Book> copy = new ArrayList<>(books);
        Collections.sort(copy);
        for (Book b : copy) b.printInfo();
    }

    public void printBooksSortedByAuthor() {
        List<Book> copy = new ArrayList<>(books);
        Collections.sort(copy, new AuthorComparator());
        for (Book b : copy) b.printInfo();
    }

    public void printBooksSortedByPublishedDate() {
        List<Book> copy = new ArrayList<>(books);
        Collections.sort(copy, new PublishedDateComparator());
        for (Book b : copy) b.printInfo();
    }

    public void printBooksSortedByRecentBorrow() {
        List<Book> copy = new ArrayList<>(books);
        Collections.sort(copy, new LastBorrowedDateComparator());
        for (Book b : copy) b.printInfo();
    }
}
