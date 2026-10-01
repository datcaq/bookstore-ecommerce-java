package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Embeddable
public class BookAuthorId_24162019 implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "bookid")
  private Integer bookid;

  @Column(name = "author_id")
  private Integer authorId;

  public BookAuthorId_24162019() {}

  public BookAuthorId_24162019(Integer a, Integer b) {
    this.bookid = a;
    this.authorId = b;
  }

  public Integer getBookid() {
    return bookid;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BookAuthorId_24162019)) return false;
    BookAuthorId_24162019 x = (BookAuthorId_24162019) o;
    return java.util.Objects.equals(bookid, x.bookid)
        && java.util.Objects.equals(authorId, x.authorId);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(bookid, authorId);
  }
}
