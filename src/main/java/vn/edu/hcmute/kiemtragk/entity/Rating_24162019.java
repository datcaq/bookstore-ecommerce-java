package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Entity(name = "Rating")
@Table(name = "rating")
public class Rating_24162019 {
  @EmbeddedId private RatingId_24162019 id;

  @MapsId("userid")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userid")
  private User_24162019 user;

  @MapsId("bookid")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bookid")
  private Book_24162019 book;

  @Column(columnDefinition = "tinyint")
  private Short rating;

  @Column(name = "review_text", columnDefinition = "text")
  @Convert(converter = VietnameseTextConverter_24162019.class)
  private String reviewText;

  public RatingId_24162019 getId() {
    return id;
  }

  public void setId(RatingId_24162019 v) {
    id = v;
  }

  public User_24162019 getUser() {
    return user;
  }

  public void setUser(User_24162019 v) {
    user = v;
  }

  public Book_24162019 getBook() {
    return book;
  }

  public void setBook(Book_24162019 v) {
    book = v;
  }

  public Short getRating() {
    return rating;
  }

  public void setRating(Short v) {
    rating = v;
  }

  public String getReviewText() {
    return reviewText;
  }

  public void setReviewText(String v) {
    reviewText = v;
  }
}
