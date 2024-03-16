package se.kth.shariff.mongolab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.kth.shariff.mongolab.model.BooksDbInterface;
import se.kth.shariff.mongolab.model.BooksImplements;
import se.kth.shariff.mongolab.view.BooksPane;

import java.io.IOException;

public class BookApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        BooksDbInterface booksDb = new BooksImplements();
        BooksPane booksPane = new BooksPane((BooksImplements) booksDb);
        Scene scene = new Scene(booksPane, 800, 600);
        primaryStage.setTitle("Book Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}