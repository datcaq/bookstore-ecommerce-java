package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Entity(name = "Book")
@Table(name = "books")
public class Book_24162019 implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookid")
  private Integer id;

  private Integer isbn;

  @Column(length = 200)
  private String title;

  @Column(length = 100)
  private String publisher;

  @Column(precision = 6, scale = 2)
  private java.math.BigDecimal price;

  @Column(columnDefinition = "text")
  @Convert(converter = VietnameseTextConverter_24162019.class)
  private String description;

  @Column(name = "publish_date")
  private java.time.LocalDate publishDate;

  @Column(name = "cover_image", length = 100)
  private String coverImage;

  private Integer quantity;

  public Integer getId() {
    return id;
  }

  public void setId(Integer v) {
    id = v;
  }

  public Integer getIsbn() {
    return isbn;
  }

  public void setIsbn(Integer v) {
    isbn = v;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String v) {
    title = v;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String v) {
    publisher = v;
  }

  public java.math.BigDecimal getPrice() {
    return price;
  }

  public void setPrice(java.math.BigDecimal v) {
    price = v;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String v) {
    description = v;
  }

  public java.time.LocalDate getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(java.time.LocalDate v) {
    publishDate = v;
  }

  public String getCoverImage() {
    return coverImage;
  }

  public void setCoverImage(String v) {
    coverImage = v;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer v) {
    quantity = v;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "book_author",
      joinColumns = @JoinColumn(name = "bookid"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private java.util.Set<Author_24162019> authors = new java.util.LinkedHashSet<>();

  @OneToMany(mappedBy = "book")
  private java.util.List<Rating_24162019> ratings = new java.util.ArrayList<>();

  @Transient private long reviewCount;

  public java.util.Set<Author_24162019> getAuthors() {
    return authors;
  }

  public void setAuthors(java.util.Set<Author_24162019> a) {
    authors = a;
  }

  public java.util.List<Rating_24162019> getRatings() {
    return ratings;
  }

  public long getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(long v) {
    reviewCount = v;
  }

  public String getCoverPath() {
    if (coverImage == null || coverImage.isBlank()) return "/assets/images/book-placeholder.svg";
    if (coverImage.matches("(?i)^https?://[^\\s]+$")) return coverImage;
    if (coverImage.matches("[a-zA-Z0-9_.-]+") && !coverImage.contains(".."))
      return "/assets/uploads/" + coverImage;
    return "/assets/images/book-placeholder.svg";
  }
}
