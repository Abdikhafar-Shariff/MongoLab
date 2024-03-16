package se.kth.shariff.mongolab.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book {
    private ObjectId bookId;
    private String isbn; // should check format
    private String title;
    private Date published;
    private String storyLine = "";
    private String genre;
    private int rating;
    private ArrayList<Author> authors;



    // TODO:
    // Add authors, as a separate class(!), and corresponding methods, to your implementation
    // as well, i.e. "private ArrayList<Author> authors;"


    public Book(ObjectId bookId, String isbn, String title, Date published, String genre, int rating) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.published = published;
        this.storyLine = storyLine;
        this.genre = genre;
        this.rating = rating;
        authors = new ArrayList<>();
    }

    public Book(String isbn, String title, Date published, String genre, int rating) {
        this(new ObjectId(), isbn, title, published, genre,rating); // Anropa huvudkonstruktorn
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public   ObjectId getBookId() { return bookId; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Date getPublished() { return published; }
    public String getStoryLine() { return storyLine; }

    public void setStoryLine(String storyLine) {
        this.storyLine = storyLine;
    }
    public void addAuthor(Author author) {
        authors.add(author);
    }
    public int getRating() {
        return rating;
    }

    public List<Author> getAuthors() {
        return  new ArrayList<>(authors);
    }

    public void setBookId(ObjectId bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return title + ", " + isbn + ", " + published.toString()+ genre + ", "+rating;
    }
}
