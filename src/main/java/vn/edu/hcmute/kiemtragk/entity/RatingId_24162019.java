package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Embeddable
public class RatingId_24162019 implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "userid")
  private Integer userid;

  @Column(name = "bookid")
  private Integer bookid;

  public RatingId_24162019() {}

  public RatingId_24162019(Integer a, Integer b) {
    this.userid = a;
    this.bookid = b;
  }

  public Integer getUserid() {
    return userid;
  }

  public Integer getBookid() {
    return bookid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RatingId_24162019)) return false;
    RatingId_24162019 x = (RatingId_24162019) o;
    return java.util.Objects.equals(userid, x.userid) && java.util.Objects.equals(bookid, x.bookid);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(userid, bookid);
  }
}
