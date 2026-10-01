package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

// Read-only view of the join table. Book.authors owns all writes.
@Entity(name = "BookAuthor")
@Table(name = "book_author")
@org.hibernate.annotations.Immutable
public class BookAuthor_24162019 {
  @EmbeddedId private BookAuthorId_24162019 id;

  @MapsId("bookid")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bookid")
  private Book_24162019 book;

  @MapsId("authorId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private Author_24162019 author;

  public BookAuthorId_24162019 getId() {
    return id;
  }

  public Book_24162019 getBook() {
    return book;
  }

  public Author_24162019 getAuthor() {
    return author;
  }
}
