Books Database and Client Application
Project Overview
This project involves creating a database to store information about books, authors, genres, and ratings, along with a client application that interacts with this database. It's designed to demonstrate a comprehensive approach to managing many-to-many relationships (e.g., between authors and books), encapsulating database access through specific user roles, and representing relational data as objects in the client application.

Key Features
Database
Many-to-Many Relationships: The database is structured to reflect many-to-many relationships, especially between authors and books, through the use of join tables.
User Access Control: Specific database users are created to represent the client application, with restricted access to tables using the GRANT SQL statement. This ensures secure and controlled access to the database.
Client Application
Object-Oriented Data Representation: Data retrieved from the database is represented as objects (e.g., Book, Author, Review, User) in the application. Relationships between database tables are mirrored in the object model, such as books having a list of associated author objects.
Database Interaction: The code for interacting with the database is organized into separate methods for specific queries, inserts, or updates. These methods return objects or lists of objects and are defined in a Java interface to abstract away the implementation details.
Error Handling: To ensure the client application can handle SQL exceptions gracefully, custom exceptions (e.g., BooksDbException) are thrown instead.
Efficient Data Retrieval: The application avoids downloading the entire database content, fetching only the relevant data for each query.
Transactions and Multithreading: Database operations are executed within transactions to allow for rollbacks in case of errors. Each database operation is also executed in a separate thread to enhance performance.
Graphical User Interface (GUI): The client application features a GUI, following the MVC design pattern, for an intuitive user experience.
Safe Database Disconnection: The application ensures a safe disconnection from the database upon exit, preventing potential data loss or corruption.
Getting Started
Prerequisites
Java Development Kit (JDK)
NOSQL database management system (MongoDb)
Any IDE that supports Java development (e.g., IntelliJ IDEA, Eclipse)
Setup
Clone the repository to your local machine.
Set up the database using the SQL scripts provided in the sql directory.
Update the database connection settings in the application configuration file.
Open the project in your IDE and resolve any dependencies (e.g., JDBC driver for your database).
Run the application from the main class.
Contributing
Contributions to the project are welcome! Please fork the repository and submit a pull request with your changes.
