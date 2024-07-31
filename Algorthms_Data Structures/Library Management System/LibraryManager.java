import java.util.Arrays;
import java.util.Scanner;

public class LibraryManager {

    static class Book {
        private int id;
        private String title;
        private String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return "Book ID: " + id + ", Title: '" + title + "', Author: '" + author + "'";
        }
    }

    private Book[] bookList = new Book[100];
    private int count = 0;

    public void addNewBook(int id, String title, String author) {
        if (count == bookList.length) {
            bookList = Arrays.copyOf(bookList, bookList.length * 2);
        }
        bookList[count++] = new Book(id, title, author);
        sortBookList();
    }

    public Book searchByTitleLinear(String title) {
        for (int i = 0; i < count; i++) {
            if (bookList[i].getTitle().equalsIgnoreCase(title)) {
                return bookList[i];
            }
        }
        return null;
    }

    public Book searchByTitleBinary(String title) {
        int left = 0;
        int right = count - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = bookList[mid].getTitle().compareToIgnoreCase(title);
            if (comparison == 0) {
                return bookList[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    private void sortBookList() {
        Arrays.sort(bookList, 0, count, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
    }

    public void displayAllBooks() {
        for (int i = 0; i < count; i++) {
            System.out.println(bookList[i]);
        }
    }

    public static void main(String[] args) {
        LibraryManager manager = new LibraryManager();
        Scanner scanner = new Scanner(System.in);
        int bookId = 1;

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add New Book");
            System.out.println("2. Search Book by Title (Linear)");
            System.out.println("3. Search Book by Title (Binary)");
            System.out.println("4. Display All Books");
            System.out.println("-1. Exit");
            System.out.print("Your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = scanner.nextLine();
                    manager.addNewBook(bookId++, title, author);
                    break;
                case 2:
                    System.out.print("Enter Title to search (Linear Search): ");
                    String linearTitle = scanner.nextLine();
                    Book linearResult = manager.searchByTitleLinear(linearTitle);
                    if (linearResult != null) {
                        System.out.println("Book Found: " + linearResult);
                    } else {
                        System.out.println("No book found with title '" + linearTitle + "'.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Title to search (Binary Search): ");
                    String binaryTitle = scanner.nextLine();
                    Book binaryResult = manager.searchByTitleBinary(binaryTitle);
                    if (binaryResult != null) {
                        System.out.println("Book Found: " + binaryResult);
                    } else {
                        System.out.println("No book found with title '" + binaryTitle + "'.");
                    }
                    break;
                case 4:
                    manager.displayAllBooks();
                    break;
                case -1:
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
