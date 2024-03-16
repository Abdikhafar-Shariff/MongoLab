package se.kth.shariff.mongolab.model;

public class BooksDbException extends Exception{

    public BooksDbException(String msg, Exception cause) {
        super(msg, cause);
    }

    public BooksDbException(String msg) {
        super(msg);
    }

    public BooksDbException() {
        super();
    }
}
