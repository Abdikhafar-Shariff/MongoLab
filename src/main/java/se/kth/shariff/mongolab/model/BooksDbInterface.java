package se.kth.shariff.mongolab.model;

import org.bson.types.ObjectId;

import java.util.List;

public interface BooksDbInterface {

    /**
     * Connect to the database.
     * @param database
     * @return true on successful connection.
     */
    public boolean connect(String database) throws BooksDbException;

    public void disconnect() throws BooksDbException;

    public List<Book> searchBooksByTitle(String title) throws BooksDbException;

    List<Book> searchBooksByISBN(String isbn) throws BooksDbException;

    List<Book> searchBooksByAuthor(String author) throws BooksDbException;
    void addBook(Book book, List<Author> authors) throws BooksDbException;


    ObjectId addAuthor(Author author) throws BooksDbException;

    List<Book> searchBooksByRating(int rating) throws BooksDbException;

    List<Book> searchBooksByGenre(String genre) throws BooksDbException;



    List<Author> getAllAuthors() throws BooksDbException;

    // TODO: Add abstract methods for all inserts, deletes and queries
    // mentioned in the instructions for the assignement.
}
