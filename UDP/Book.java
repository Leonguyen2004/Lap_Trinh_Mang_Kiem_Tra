package UDP;

import java.io.Serializable;

public class Book implements Serializable {
    public String id, title, author, isbn, publishDate;
    private static final long serialVersionUID = 20251107L;

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }
}
