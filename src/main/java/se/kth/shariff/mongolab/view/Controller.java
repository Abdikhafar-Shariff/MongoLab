package se.kth.shariff.mongolab.view;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import se.kth.shariff.mongolab.model.*;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.*;

public class Controller {
    private final BooksPane booksView; // view
    private final BooksDbInterface booksDb; // model

    public Controller(BooksDbInterface booksDb, BooksPane booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        new Thread(() -> {
            try {
                if (searchFor != null && !searchFor.isEmpty()) {
                    List<Book> result = null;

                    switch (mode) {
                        case Title:
                            result = booksDb.searchBooksByTitle(searchFor);
                            System.out.println("filippe "+result);
                            break;
                        case ISBN:
                            result = booksDb.searchBooksByISBN(searchFor);
                            break;
                        case Author:
                            result = booksDb.searchBooksByAuthor(searchFor);
                            break;
                        case genre:
                            result = booksDb.searchBooksByGenre(searchFor);
                            break;
                        case rating:
                            result = booksDb.searchBooksByRating(Integer.parseInt(searchFor));

                            break;
                        default:
                            result = new ArrayList<>();
                    }
                    final List<Book> finalResult = result;
                    System.out.println( " "+finalResult);
                    Platform.runLater(() -> {
                        if (finalResult == null || finalResult.isEmpty()) {
                            booksView.showAlertAndWait("No results found.", INFORMATION);
                        } else {
                            booksView.displayBooks(finalResult);
                        }
                    });
                } else {
                    Platform.runLater(() -> booksView.showAlertAndWait("Enter a search string!", WARNING));
                }
            } catch (Exception e) {
                Platform.runLater(() -> booksView.showAlertAndWait("Database error: " + e.getMessage(), ERROR));
            }
        }).start();
    }

    public void connectToDatabase() {
        try {
            booksDb.connect("nebil23");
            booksView.showAlertAndWait("Connected to database.", INFORMATION);
        } catch (BooksDbException e) {
            booksView.showAlertAndWait("Failed to connect to database.", ERROR);
        }
    }
    public void disconnectFromDatabase() {
        try {
            booksDb.disconnect();
            booksView.showAlertAndWait("Disconnected from database.", INFORMATION);
        } catch (BooksDbException e) {
            booksView.showAlertAndWait("Failed to disconnect from database.", ERROR);
        }
    }

    public void addAuthor(Author author) {
        new Thread(() -> {
            try {
                booksDb.addAuthor(author);
                Platform.runLater(() ->
                        booksView.showAlertAndWait("Author added successfully.", Alert.AlertType.INFORMATION));
            } catch (BooksDbException e) {
                Platform.runLater(() ->
                        booksView.showAlertAndWait("Failed to add author: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        }).start();
    }
    public List<Author> fetchAllAuthors() {
        try {
            return booksDb.getAllAuthors();
        } catch (BooksDbException e) {
            booksView.showAlertAndWait("Failed to fetch authors: " + e.getMessage(), Alert.AlertType.ERROR);
            return new ArrayList<>();
        }
    }

   public void handleAddBookWithAuthors(Book book, List<Author> authors) {
        new Thread(() -> {
            try {
                booksDb.addBook(book,authors);
                Platform.runLater(() -> booksView.showAlertAndWait("Book and authors added successfully.", Alert.AlertType.INFORMATION));
            } catch (BooksDbException e) {
                Platform.runLater(() -> booksView.showAlertAndWait("Failed to add book with authors: " + e.getMessage(), Alert.AlertType.ERROR));
            }
        }).start();
    }
}
