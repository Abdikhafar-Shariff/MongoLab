package se.kth.shariff.mongolab.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;


public class BooksImplements implements BooksDbInterface {
    private MongoDatabase db;


    public boolean connect(String database) throws BooksDbException {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            db = mongoClient.getDatabase(database);

            return true;
        } catch (Exception e) {
            throw new BooksDbException("Failed to connect to MongoDB", e);
        }
    }

    @Override
    public void disconnect() throws BooksDbException {

    }


    public List<Book> searchBooksByTitle(String title) throws BooksDbException {

        List<Book> books = new ArrayList<>();
        try {
            MongoCollection<Document> collection = db.getCollection("Book");
            for (Document doc : collection.find(Filters.regex("title", title, "i"))) { // "i" för att ignorera skiftläge
                ObjectId bookId = doc.getObjectId("bookId");
                String isbn = doc.getString("isbn");
                String bookTitle = doc.getString("title"); // Använd 'bookTitle' för att undvika förväxling med 'title'-parametern
                Date published = doc.getDate("published");
                String genre = doc.getString("genre");
                int rating = doc.getInteger("rating");
                Book book = new Book(bookId, isbn, bookTitle, published, genre, rating);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BooksDbException("Failed to search books by title in MongoDB", e);
        }
        return books;
    }

    @Override
    public List<Book> searchBooksByISBN(String isbn) throws BooksDbException {

        List<Book> books = new ArrayList<>();
        try {
            MongoCollection<Document> collection = db.getCollection("Book");
            for (Document doc : collection.find(Filters.eq("isbn", isbn))) {
                ObjectId bookId = doc.getObjectId("bookId");
                String bookIsbn = doc.getString("isbn");
                String title = doc.getString("title");
                Date published = doc.getDate("published");
                String genre = doc.getString("genre");
                int rating = doc.getInteger("rating");
                Book book = new Book(bookId, bookIsbn, title, published, genre, rating);
                books.add(book);
            }
        } catch (Exception e) {
            throw new BooksDbException("Failed to search books by ISBN in MongoDB", e);
        }
        return books;

    }

    public List<Book> searchBooksByAuthor(String authorName) throws BooksDbException {
        List<Book> books = new ArrayList<>();
        try {
            MongoCollection<Document> booksCollection = db.getCollection("Book");
            FindIterable<Document> bookDocs = booksCollection.find(Filters.in("authors", authorName));
            for (Document doc : bookDocs) {
                if (doc != null) {
                    ObjectId bookId = doc.getObjectId("_id");
                    String isbn = doc.getString("isbn");
                    String title = doc.getString("title");
                    Date published = doc.getDate("published");
                    String genre = doc.getString("genre");
                    int rating = doc.getInteger("rating");
                    Book book = new Book(bookId, isbn, title, published, genre, rating);
                    books.add(book);
                }
            }
        } catch (MongoException e) {
            throw new BooksDbException("Failed to search books by author in MongoDB", e);
        }
        return books;
    }

    @Override
    public void addBook(Book book, List<Author> authors) throws BooksDbException {
        try {
            MongoCollection<Document> booksCollection = db.getCollection("Book");
            /*List<String> authorNames = author.stream()
                    .map(Author::getName)
                    .collect(Collectors.toList());*/
            List<Document> authorDocs = new ArrayList<>();
            for (Author author : authors) {
                Document authorDoc = new Document()
                        .append("name", author.getName())
                        .append("birthdate", author.getBirthdate());
                // ... other author fields ...
                authorDocs.add(authorDoc);
            }

            Document bookDoc = new Document()
                    .append("title", book.getTitle())
                    .append("isbn", book.getIsbn())
                    .append("published", book.getPublished())
                    .append("genre", book.getGenre())
                    .append("rating", book.getRating())
                    .append("authors", authorDocs);  // Storing author names in the Book document

            // Insert the book document into the collection
            booksCollection.insertOne(bookDoc);

            // Optionally, set the MongoDB-generated ID back on the book object
            book.setBookId(bookDoc.getObjectId("_id"));
        } catch (MongoException e) {
            throw new BooksDbException("Failed to add book with authors", e);
        }
    }




    @Override
    public ObjectId addAuthor(Author author) throws BooksDbException {
        try {
            MongoCollection<Document> authorsCollection = db.getCollection("authors");
            Document authorDoc = new Document() // Convert Author to Document
                    .append("name", author.getName())
                    .append("birthdate", author.getBirthdate());
            // ... other author fields ...

            authorsCollection.insertOne(authorDoc);
            return authorDoc.getObjectId("_id"); // Return the generated ObjectId
        } catch (Exception e) {
            throw new BooksDbException("Failed to add author", e);
        }
    }


    @Override
    public List<Book> searchBooksByRating(int rating) throws BooksDbException {

        List<Book> books = new ArrayList<>();
        try {
            MongoCollection<Document> collection = db.getCollection("Book");
            for (Document doc : collection.find(Filters.eq("rating", rating))) {
                ObjectId bookId = doc.getObjectId("bookId");
                String isbn = doc.getString("isbn");
                String title = doc.getString("title");
                Date published = doc.getDate("published");
                String genre = doc.getString("genre");
                int bookRating = doc.getInteger("rating");

                Book book = new Book(bookId, isbn, title, published, genre, bookRating);
                books.add(book);
            }
        } catch (Exception e) {
            throw new BooksDbException("Failed to search books by rating in MongoDB", e);
        }
        return books;

    }

    @Override
    public List<Book> searchBooksByGenre(String genre) throws BooksDbException {
        List<Book> books = new ArrayList<>();
        try {
            MongoCollection<Document> booksCollection = db.getCollection("Book");
            for (Document doc : booksCollection.find(Filters.eq("genre", genre))) {
                ObjectId bookId = doc.getObjectId("_id");
                String isbn = doc.getString("isbn");
                String title = doc.getString("title");
                Date published = doc.getDate("published");
                String bookGenre = doc.getString("genre");
                int rating = doc.getInteger("rating");

                // Antag att du har en konstruktör som matchar dessa parametrar
                Book book = new Book(bookId, isbn, title, published, bookGenre, rating);
                books.add(book);
            }
        } catch (Exception e) {
            throw new BooksDbException("Failed to search books by genre", e);
        }
        return books;
    }






    @Override
    public List<Author> getAllAuthors() throws BooksDbException {
        List<Author> authors = new ArrayList<>();
        try {
            MongoCollection<Document> authorsCollection = db.getCollection("authors");
            for (Document doc : authorsCollection.find()) {
                ObjectId id = doc.getObjectId("_id");
                String name = doc.getString("name");
                Date birthdate = doc.getDate("birthdate");

                Author author = new Author(id, name, birthdate);

                authors.add(author);
            }
        } catch (Exception e) {
            throw new BooksDbException("Failed to retrieve authors", e);
        }
        return authors;
    }
}
