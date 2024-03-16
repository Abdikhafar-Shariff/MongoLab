package se.kth.shariff.mongolab.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Author {
    private ObjectId id;
    private String name;
    private Date birthdate;
    // Eventuellt andra attribut som biografi, nationalitet etc.

    // Konstruktorer
    public Author(String name, Date birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public Author(ObjectId id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public Author(String name) {
        this.name = name;

    }

    // Getters och Setters
    public ObjectId getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    // Övriga metoder
    // Exempelvis en metod för att skriva ut författarens information
    @Override
    public String toString() {
        return name;
    }

}
