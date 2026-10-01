package vn.edu.hcmute.kiemtragk;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;
import vn.edu.hcmute.kiemtragk.config.*;
import vn.edu.hcmute.kiemtragk.dao.impl.*;
import vn.edu.hcmute.kiemtragk.entity.*;
import vn.edu.hcmute.kiemtragk.service.*;
import vn.edu.hcmute.kiemtragk.service.impl.*;
import vn.edu.hcmute.kiemtragk.util.*;

/**
 * Real Hibernate + in-memory test DB + Tomcat 10.1/Jasper HTTP integration. No SQL Server secrets.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookStoreTest {
  @Test
  @Order(15)
  void jakartaMailProviderAndVietnameseMimeBody() throws Exception {
    var session = jakarta.mail.Session.getInstance(new Properties());
    try (var smtp = session.getTransport("smtp")) {
      assertTrue(smtp.getClass().getName().startsWith("org.eclipse.angus.mail.smtp."));
    }
    var message = new jakarta.mail.internet.MimeMessage(session);
    message.setFrom(new jakarta.mail.internet.InternetAddress("sender@example.com"));
    message.setRecipient(
        jakarta.mail.Message.RecipientType.TO,
        new jakarta.mail.internet.InternetAddress("recipient@example.com"));
    message.setSubject("Mã xác thực đăng ký", "UTF-8");
    message.setText("Mã OTP: 123456. Có hiệu lực 5 phút.", "UTF-8");
    var output = new java.io.ByteArrayOutputStream();
    message.writeTo(output);
    var decoded =
        new jakarta.mail.internet.MimeMessage(
            session, new java.io.ByteArrayInputStream(output.toByteArray()));
    assertEquals("Mã xác thực đăng ký", decoded.getSubject());
    assertTrue(decoded.getContent().toString().contains("Có hiệu lực 5 phút"));
  }

  static EntityManagerFactory emf;
  static Tomcat tomcat;
  static String root;
  static BookService_24162019 books;
  static AuthorService_24162019 authors;
  static RatingService_24162019 ratings;
  static UserService_24162019 users;
  static int uid, aid, firstBook, firstAuthor;
  static final CaptureMail mail = new CaptureMail();

  static class CaptureMail extends EmailUtil_24162019 {
    String lastCode;

    @Override
    public void sendOtp(String email, String otp) {
      lastCode = otp;
    }
  }

  static HttpClient client() {
    return HttpClient.newBuilder()
        .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
        .followRedirects(HttpClient.Redirect.NEVER)
        .build();
  }

  static HttpResponse<String> get(HttpClient c, String path) throws Exception {
    return c.send(
        HttpRequest.newBuilder(URI.create(root + path)).GET().build(),
        HttpResponse.BodyHandlers.ofString());
  }

  static HttpResponse<String> post(HttpClient c, String path, String data) throws Exception {
    return c.send(
        HttpRequest.newBuilder(URI.create(root + path))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(data))
            .build(),
        HttpResponse.BodyHandlers.ofString());
  }

  static String encode(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  static String token(String html) {
    var m = java.util.regex.Pattern.compile("name=\"csrf\" value=\"([^\"]+)\"").matcher(html);
    assertTrue(m.find(), html);
    return m.group(1);
  }

  static HttpClient login(String email, String password) throws Exception {
    HttpClient c = client();
    String csrf = token(get(c, "/login").body());
    var r =
        post(
            c,
            "/login",
            "csrf=" + csrf + "&email=" + encode(email) + "&password=" + encode(password));
    assertEquals(302, r.statusCode(), r.body());
    return c;
  }

  static Book_24162019 book(String title) {
    Book_24162019 b = new Book_24162019();
    b.setTitle(title);
    b.setIsbn(1234);
    b.setPublisher("Nhà xuất bản Văn học");
    b.setPrice(new BigDecimal("95.00"));
    b.setQuantity(12);
    b.setDescription("Sách rất hay, giàu cảm xúc.");
    b.setPublishDate(LocalDate.of(2024, 1, 1));
    return b;
  }

  @BeforeAll
  static void setup() throws Exception {
    Map<String, Object> opts = new HashMap<>();
    opts.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
    opts.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:bookstore;DB_CLOSE_DELAY=-1");
    opts.put("jakarta.persistence.jdbc.user", "sa");
    opts.put("jakarta.persistence.jdbc.password", "");
    opts.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    opts.put("hibernate.hbm2ddl.auto", "create-drop");
    emf = Persistence.createEntityManagerFactory("BookStoreGK_24162019", opts);
    var field = JpaConfig_24162019.class.getDeclaredField("factory");
    field.setAccessible(true);
    field.set(null, emf);
    books = new BookServiceImpl_24162019();
    authors = new AuthorServiceImpl_24162019();
    ratings = new RatingServiceImpl_24162019();
    users = new UserServiceImpl_24162019(new UserDAOImpl_24162019(), mail);
    for (int i = 1; i <= 7; i++) {
      Author_24162019 a = new Author_24162019();
      a.setName("Tác giả " + i);
      a.setDateOfBirth(LocalDate.of(1980, 1, i));
      authors.save(a);
      if (i == 1) firstAuthor = a.getId();
    }
    for (int i = 1; i <= 12; i++) {
      var b = book("Cuốn sách " + i);
      books.save(b, Set.of(firstAuthor));
      if (i == 1) firstBook = b.getId();
    }
    UserDAOImpl_24162019 dao = new UserDAOImpl_24162019();
    for (boolean admin : new boolean[] {false, true}) {
      User_24162019 u = new User_24162019();
      u.setEmail(admin ? "admin@example.com" : "user@example.com");
      u.setFullname(admin ? "Cao Quốc Đạt" : "Nguyễn Văn An");
      u.setPasswd(PasswordUtil_24162019.hash(admin ? "Admin@123" : "User@123"));
      u.setAdmin(admin);
      u.setSignupDate(LocalDateTime.now());
      dao.create(u);
      if (admin) aid = u.getId();
      else uid = u.getId();
    }
    Path web = Path.of("target/test-webapp").toAbsolutePath();
    try (var files = Files.walk(Path.of("src/main/webapp"))) {
      for (Path src : files.toList()) {
        Path dst = web.resolve(Path.of("src/main/webapp").relativize(src));
        if (Files.isDirectory(src)) Files.createDirectories(dst);
        else Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
      }
    }
    Path lib = web.resolve("WEB-INF/lib");
    Files.createDirectories(lib);
    // Jakarta JSTL separates the API from the implementation containing TLD files.
    for (Class<?> type :
        new Class<?>[] {
          jakarta.servlet.jsp.jstl.core.Config.class,
          org.apache.taglibs.standard.tag.rt.core.OutTag.class
        }) {
      Path jar = Path.of(type.getProtectionDomain().getCodeSource().getLocation().toURI());
      Files.copy(jar, lib.resolve(jar.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }
    tomcat = new Tomcat();
    tomcat.setBaseDir("target/tomcat-test");
    tomcat.setPort(0);
    tomcat.getConnector();
    Context ctx = tomcat.addWebapp("/kiemtragk", web.toString());
    ctx.setParentClassLoader(BookStoreTest.class.getClassLoader());
    ((org.apache.tomcat.util.scan.StandardJarScanner) ctx.getJarScanner()).setScanManifest(false);
    tomcat.start();
    root = "http://localhost:" + tomcat.getConnector().getLocalPort() + "/kiemtragk";
    // Replace SMTP only; the HTTP flow still calls real service/DAO and session logic.
    for (String name : new String[] {"Register", "VerifyOtp"}) {
      Wrapper wrapper = (Wrapper) ctx.findChild(name);
      jakarta.servlet.Servlet servlet = wrapper.allocate();
      var f = servlet.getClass().getDeclaredField("users");
      f.setAccessible(true);
      f.set(servlet, users);
      wrapper.deallocate(servlet);
    }
  }

  @AfterAll
  static void stop() throws Exception {
    if (tomcat != null) {
      tomcat.stop();
      tomcat.destroy();
    }
    JpaConfig_24162019.close();
  }

  @Test
  @Order(1)
  void registerWrongThenCorrectOtpOverHttp() throws Exception {
    var c = client();
    String csrf = token(get(c, "/register").body());
    String data =
        "csrf="
            + csrf
            + "&email=new%40example.com&fullname="
            + encode("Nguyễn Quốc Đạt")
            + "&phone=912345678&password=New%40123&confirm=New%40123";
    assertEquals(302, post(c, "/register", data).statusCode());
    assertNull(new UserDAOImpl_24162019().findByEmail("new@example.com"));
    var page = get(c, "/verify-otp");
    assertEquals(200, page.statusCode(), page.body());
    csrf = token(page.body());
    assertFalse(page.body().contains("data-decorator="));
    var wrong = post(c, "/verify-otp", "csrf=" + csrf + "&otp=wrong");
    assertTrue(wrong.body().contains("Mã OTP không đúng"), wrong.body());
    assertNull(new UserDAOImpl_24162019().findByEmail("new@example.com"));
    assertEquals(
        302, post(c, "/verify-otp", "csrf=" + csrf + "&otp=" + mail.lastCode).statusCode());
    var u = new UserDAOImpl_24162019().findByEmail("new@example.com");
    assertNotNull(u);
    assertFalse(u.getAdmin());
    assertNotNull(u.getSignupDate());
    assertTrue(get(c, "/verify-otp").body().contains("Không có đăng ký"));
  }

  @Test
  @Order(2)
  void otpExpiryAndAttemptLimit() {
    var u = new User_24162019();
    u.setEmail("expired@example.com");
    assertThrows(
        IllegalArgumentException.class,
        () ->
            users.verify(
                new PendingRegistration_24162019(u, "123456", Instant.now().minusSeconds(1)),
                "123456"));
    var p = new PendingRegistration_24162019(u, "123456", Instant.now().plusSeconds(300));
    for (int i = 0; i < 5; i++)
      assertThrows(IllegalArgumentException.class, () -> p.verify("000000"));
    assertThrows(IllegalArgumentException.class, () -> p.verify("123456"));
    assertNull(new UserDAOImpl_24162019().findByEmail(u.getEmail()));
  }

  @Test
  @Order(3)
  void invalidLogin() throws Exception {
    var c = client();
    String t = token(get(c, "/login").body());
    var r = post(c, "/login", "csrf=" + t + "&email=user%40example.com&password=wrong");
    assertEquals(200, r.statusCode());
    assertTrue(r.body().contains("Email hoặc mật khẩu không đúng"));
  }

  @Test
  @Order(4)
  void userLoginAndAdminProtection() throws Exception {
    var c = login("user@example.com", "User@123");
    var h = get(c, "/home");
    assertEquals(200, h.statusCode(), h.body());
    assertTrue(h.body().contains("Nguyễn Văn An"));
    assertFalse(h.body().contains(">Trang quản trị</a>"));
    assertEquals(403, get(c, "/admin/books").statusCode());
    assertEquals(403, get(c, "/admin/authors/add").statusCode());
    assertNotNull(new UserDAOImpl_24162019().findByEmail("user@example.com").getLastLogin());
  }

  @Test
  @Order(5)
  void adminDecoratorAndDashboard() throws Exception {
    var c = login("admin@example.com", "Admin@123");
    var r = get(c, "/admin");
    assertEquals(200, r.statusCode(), r.body());
    assertTrue(r.body().contains("data-decorator=\"admin\""), r.body());
    assertTrue(r.body().contains("Quản lý Books"));
    assertTrue(r.body().contains("Cao Quốc Đạt - 24162019 - Mã đề 01"));
  }

  @Test
  @Order(6)
  void homePaginationAndWebDecorator() throws Exception {
    var c = client();
    var p1 = get(c, "/home?page=1");
    var p2 = get(c, "/home?page=2");
    assertEquals(200, p1.statusCode(), p1.body());
    assertEquals(6, p1.body().split("class=\"book-card\"", -1).length - 1);
    assertEquals(6, p2.body().split("class=\"book-card\"", -1).length - 1);
    assertTrue(p1.body().contains("data-decorator=\"web\""));
    assertTrue(p1.body().matches("(?s).*Cuốn sách 1\\s*</a>.*"));
    assertFalse(p2.body().matches("(?s).*Cuốn sách 1\\s*</a>.*"));
    assertTrue(p2.body().contains("Cuốn sách 12"));
    assertTrue(p1.body().contains("/book/detail?id=" + firstBook));
    assertEquals(200, get(c, "/").statusCode());
  }

  @Test
  @Order(7)
  void detailAndReviewUpsert() throws Exception {
    var c = login("user@example.com", "User@123");
    var page = get(c, "/book/detail?id=" + firstBook);
    assertEquals(200, page.statusCode(), page.body());
    assertTrue(page.body().contains("Tác giả 1"));
    String t = token(page.body());
    long before = books.find(firstBook).getReviewCount();
    String review =
        "csrf="
            + t
            + "&bookId="
            + firstBook
            + "&userid="
            + aid
            + "&rating=5&review_text="
            + encode("Sách rất hay <script>alert(1)</script>");
    assertEquals(302, post(c, "/book/review", review).statusCode());
    assertEquals(before + 1, books.find(firstBook).getReviewCount());
    var rs = ratings.forBook(firstBook);
    assertEquals(uid, rs.get(0).getUser().getId());
    var html = get(c, "/book/detail?id=" + firstBook).body();
    assertTrue(html.contains("&lt;script&gt;"));
    assertFalse(html.contains("<script>alert"));
    assertEquals(302, post(c, "/book/review", review.replace("rating=5", "rating=4")).statusCode());
    assertEquals(before + 1, books.find(firstBook).getReviewCount());
    assertEquals((short) 4, ratings.forBook(firstBook).get(0).getRating());
  }

  @Test
  @Order(8)
  void invalidReviewAndAnonymousBlocked() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> ratings.save(uid, firstBook, 6, "No"));
    assertThrows(IllegalArgumentException.class, () -> ratings.save(uid, firstBook, 5, " "));
    assertEquals(
        302, post(client(), "/book/review", "bookId=1&rating=5&review_text=test").statusCode());
  }

  @Test
  @Order(9)
  void bookCrudAndRelations() throws Exception {
    var c = login("admin@example.com", "Admin@123");
    String csrf = token(get(c, "/admin/books/add").body());
    long before = books.count();
    String data =
        "csrf="
            + csrf
            + "&isbn=2026&title="
            + encode("Sách kiểm thử")
            + "&publisher="
            + encode("Nhà xuất bản Trẻ")
            + "&price=99.99&quantity=10&publish_date=2026-01-01&authors="
            + firstAuthor
            + "&authors="
            + (firstAuthor + 1)
            + "&description="
            + encode("Nội dung tiếng Việt");
    assertEquals(302, post(c, "/admin/books/add", data).statusCode());
    assertEquals(before + 1, books.count());
    var b = books.page(3).get(0);
    assertEquals(2, b.getAuthors().size());
    assertEquals("Nội dung tiếng Việt", b.getDescription());
    var form = get(c, "/admin/books/edit?id=" + b.getId());
    assertEquals(200, form.statusCode(), form.body());
    assertTrue(form.body().contains("checked"));
    assertEquals(
        302,
        post(
                c,
                "/admin/books/edit",
                data.replace("price=99.99", "price=75.50")
                        .replace("&authors=" + (firstAuthor + 1), "")
                    + "&id="
                    + b.getId())
            .statusCode());
    assertEquals(new BigDecimal("75.50"), books.find(b.getId()).getPrice());
    assertEquals(1, books.find(b.getId()).getAuthors().size());
    ratings.save(uid, b.getId(), 5, "Đánh giá để kiểm tra xóa khóa ngoại");
    assertEquals(
        302, post(c, "/admin/books/delete", "csrf=" + csrf + "&id=" + b.getId()).statusCode());
    assertNull(books.find(b.getId()));
    assertTrue(ratings.forBook(b.getId()).isEmpty());
    assertEquals(before, books.count());
  }

  @Test
  @Order(10)
  void bookListPagination() throws Exception {
    var c = login("admin@example.com", "Admin@123");
    var r = get(c, "/admin/books?page=2");
    assertEquals(200, r.statusCode(), r.body());
    assertTrue(r.body().contains("Cuốn sách 12"));
    assertFalse(r.body().matches("(?s).*Cuốn sách 1\\s*</a>.*"));
  }

  @Test
  @Order(11)
  void authorCrudAndPagination() throws Exception {
    var c = login("admin@example.com", "Admin@123");
    var list = get(c, "/admin/authors?page=2");
    assertEquals(200, list.statusCode(), list.body());
    assertTrue(list.body().contains("Tác giả 7"));
    assertFalse(list.body().matches("(?s).*Tác giả 1\\s*</td>.*"));
    String t = token(get(c, "/admin/authors/add").body());
    long before = authors.count();
    assertEquals(
        302,
        post(
                c,
                "/admin/authors/add",
                "csrf=" + t + "&author_name=" + encode("Tác giả mới") + "&date_of_birth=1990-01-01")
            .statusCode());
    assertEquals(before + 1, authors.count());
    int id =
        authors.all().stream()
            .filter(a -> a.getName().equals("Tác giả mới"))
            .findFirst()
            .orElseThrow()
            .getId();
    assertEquals(200, get(c, "/admin/authors/edit?id=" + id).statusCode());
    assertEquals(
        302,
        post(
                c,
                "/admin/authors/edit",
                "csrf="
                    + t
                    + "&id="
                    + id
                    + "&author_name="
                    + encode("Tác giả đã sửa")
                    + "&date_of_birth=1991-02-02")
            .statusCode());
    assertEquals("Tác giả đã sửa", authors.find(id).getName());
    var b = book("Xóa tác giả liên kết");
    books.save(b, Set.of(id));
    assertEquals(302, post(c, "/admin/authors/delete", "csrf=" + t + "&id=" + id).statusCode());
    assertNull(authors.find(id));
    assertTrue(books.find(b.getId()).getAuthors().isEmpty());
    books.delete(b.getId());
  }

  @Test
  @Order(12)
  void csrfLogoutAndBadIds() throws Exception {
    var c = login("admin@example.com", "Admin@123");
    assertEquals(403, post(c, "/admin/books/delete", "id=" + firstBook).statusCode());
    assertNotNull(books.find(firstBook));
    assertEquals(404, get(c, "/book/detail?id=99999").statusCode());
    assertEquals(400, get(c, "/book/detail?id=bad").statusCode());
    String t = token(get(c, "/admin").body());
    assertEquals(302, post(c, "/logout", "csrf=" + t).statusCode());
    assertEquals(302, get(c, "/admin/books").statusCode());
  }

  @Test
  @Order(13)
  void duplicateEmailAndInvalidRegistration() {
    assertThrows(
        IllegalArgumentException.class,
        () -> users.register("user@example.com", "User", "1", "User@123", "User@123"));
    assertThrows(
        IllegalArgumentException.class,
        () -> users.register("invalid", "User", "1", "User@123", "User@123"));
    assertThrows(
        IllegalArgumentException.class,
        () -> users.register("valid@example.com", "User", "1", "User@123", "different"));
  }

  @Test
  @Order(14)
  void vietnameseTextRoundTripAndPasswordFormat() {
    var cv = new VietnameseTextConverter_24162019();
    String text = "Nguyễn Quốc Đạt: Sách rất hay, bổ ích và đầy cảm xúc.";
    assertEquals(text, cv.convertToEntityAttribute(cv.convertToDatabaseColumn(text)));
    String h = PasswordUtil_24162019.hash("Hello@123");
    assertEquals(32, h.length());
    assertTrue(PasswordUtil_24162019.matches("Hello@123", h));
    assertFalse(PasswordUtil_24162019.matches("wrong", h));
    assertNotEquals(h, PasswordUtil_24162019.hash("Hello@123"));
  }
}
