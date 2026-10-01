package vn.edu.hcmute.kiemtragk.entity;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "users")
public class User_24162019 implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(length = 50)
  @org.hibernate.annotations.Nationalized
  private String fullname;

  private Integer phone;

  @Column(nullable = false, length = 32)
  private String passwd;

  @Column(name = "signup_date")
  private java.time.LocalDateTime signupDate;

  @Column(name = "last_login")
  private java.time.LocalDateTime lastLogin;

  @Column(name = "is_admin")
  private Boolean admin;

  public Integer getId() {
    return id;
  }

  public void setId(Integer v) {
    id = v;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String v) {
    email = v;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String v) {
    fullname = v;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer v) {
    phone = v;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String v) {
    passwd = v;
  }

  public java.time.LocalDateTime getSignupDate() {
    return signupDate;
  }

  public void setSignupDate(java.time.LocalDateTime v) {
    signupDate = v;
  }

  public java.time.LocalDateTime getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(java.time.LocalDateTime v) {
    lastLogin = v;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean v) {
    admin = v;
  }

  @OneToMany(mappedBy = "user")
  private java.util.List<Rating_24162019> ratings = new java.util.ArrayList<>();

  public java.util.List<Rating_24162019> getRatings() {
    return ratings;
  }
}
