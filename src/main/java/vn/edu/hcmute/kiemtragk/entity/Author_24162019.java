package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Entity(name = "Author")
@Table(name = "author")
public class Author_24162019 implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "author_id")
  private Integer id;

  @Column(name = "author_name", length = 100)
  private String name;

  @Column(name = "date_of_birth")
  private java.time.LocalDate dateOfBirth;

  public Integer getId() {
    return id;
  }

  public void setId(Integer v) {
    id = v;
  }

  public String getName() {
    return name;
  }

  public void setName(String v) {
    name = v;
  }

  public java.time.LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(java.time.LocalDate v) {
    dateOfBirth = v;
  }

  @ManyToMany(mappedBy = "authors")
  private java.util.Set<Book_24162019> books = new java.util.LinkedHashSet<>();

  public java.util.Set<Book_24162019> getBooks() {
    return books;
  }
}
