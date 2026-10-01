# Migration Tomcat 10.1 / Jakarta – 24162019

Migration trực tiếp từ project hiện có; giữ Maven WAR, Java 17, MVC ba tầng,
Servlet/JSP, các service/DAO, SQL Server và tên class có hậu tố MSSV.
Không thay schema hay chạy lại seed trong lần migration này.

## Dependency

| Thành phần | Trước | Sau |
|---|---|---|
| Servlet | javax.servlet-api 4.0.1 | jakarta.servlet-api 6.0.0, provided |
| JSP | javax.servlet.jsp-api 2.3.3 | jakarta.servlet.jsp-api 3.1.1, provided |
| EL | Qua thư viện cũ | jakarta.el-api 5.0.1, provided |
| JSTL | javax.servlet:jstl 1.2 | jakarta.servlet.jsp.jstl-api 3.0.2 + org.glassfish.web:jakarta.servlet.jsp.jstl 3.0.1 |
| JPA | javax.persistence (Hibernate 5) | jakarta.persistence-api 3.1.0 |
| Hibernate | org.hibernate:hibernate-core 5.6.15.Final | org.hibernate.orm:hibernate-core 6.6.29.Final |
| SiteMesh | 3.0.1 | 3.2.1 (nhánh Jakarta) |
| Mail | com.sun.mail:javax.mail 1.6.2 | org.eclipse.angus:jakarta.mail 2.0.3 (API + implementation) |
| Tomcat test/Jasper | 9.0.118 | 10.1.60, test scope |
| SQL Server JDBC | 12.8.1.jre11 | Giữ nguyên |

## Namespace và cấu hình

- Servlet/Filter/Session/Listener: `javax.servlet` → `jakarta.servlet`.
- Entity, DAO, EntityManager, Persistence: `javax.persistence` → `jakarta.persistence`.
- Thuộc tính cấu hình JDBC: `jakarta.persistence.jdbc.*`.
- EmailUtil: `javax.mail` → `jakarta.mail`; giữ SMTP Gmail 587, STARTTLS, cấu hình local.
- JSP taglib: `jakarta.tags.core`, `jakarta.tags.functions`; URI fmt tương ứng là `jakarta.tags.fmt`.
- Trang lỗi đọc thuộc tính `jakarta.servlet.error.status_code`.
- `web.xml`: Jakarta Servlet 6.0; giữ nguyên servlet/filter mappings và session 30 phút.
- `persistence.xml`: namespace Jakarta, schema XML 3.0 được công bố cho Jakarta
  Persistence 3.0/3.1; API vẫn là 3.1. Vẫn liệt kê cả hai EmbeddedId và converter.
- Hibernate SQL Server: dùng `org.hibernate.dialect.SQLServerDialect` thay tên dialect cũ.
- SiteMesh 3.2 mặc định thêm `/WEB-INF/decorators/`. Filter đặt `setDecoratorPrefix("")`
  để giữ nguyên đường dẫn `/decorators/web.jsp`, `/decorators/admin.jsp`; không bỏ decorator.
- Eclipse: Dynamic Web Module 6.0, JPA facet 3.1, JPA platform `generic3_1`, Java 17.
  Đã xác nhận plugin JPA của Spring Tools trên máy có hỗ trợ platform này.
- Context root giữ **`/kiemtragk`**, trùng WAR `kiemtragk.war` và cấu hình Eclipse.

Source chính và source test không còn namespace Servlet/Persistence/Mail cũ.
Hai import `javax.crypto.SecretKeyFactory` và `javax.crypto.spec.PBEKeySpec` được giữ
vì thuộc Java SE, không có bản đổi tên tương ứng sang Jakarta. Password và tài khoản
hiện có tiếp tục dùng được với cách băm cũ.

## Kiểm chứng

Bộ HTTP regression chạy Hibernate 6.6 + H2 riêng trong RAM, Tomcat 10.1.60/Jasper
thật. Các trường hợp bao gồm đăng ký/OTP sai/đúng/hết hạn, login/logout/session,
USER bị chặn, ADMIN được phép, home 6 sách/trang, detail/review/count/upsert,
CRUD và xóa FK Books/Authors, phân trang cả hai, decorator WEB/ADMIN, CSRF, XSS.
Kiểm tra thêm Jakarta Mail SMTP provider và mã hóa/đọc lại nội dung MIME tiếng Việt,
không gửi mail ra ngoài khi chạy test.

Kết quả cuối và giới hạn kiểm thử ghi tại `TEST_REPORT.md`.
SQL Server JDBC bằng mật khẩu `sa` và gửi Gmail thật cần cấu hình local của người dùng;
test H2 hoặc test MIME không thay thế việc xác nhận hai kết nối thực này.

## Triển khai trong Eclipse

1. Stop server nếu đang chạy. Chọn KIEM_TRA_GK, F5, Maven > Update Project (Alt+F5).
2. Properties > Project Facets: Java 17, Dynamic Web Module 6.0, JPA 3.1.
3. Properties > Targeted Runtimes: chọn Apache Tomcat v10.1, runtime dùng JDK 17.
4. Tab Servers > nhấp đúp Tomcat v10.1 > Ports > HTTP/1.1 = **8081**, Ctrl+S.
   Lúc kiểm tra, cấu hình server Eclipse vẫn là 8080. Cấu hình này nằm ngoài thư mục
   bài làm nên chưa sửa; không dừng Docker để giải phóng 8080.
5. Add and Remove… > thêm KIEM_TRA_GK nếu chưa có. Modules > Path = `/kiemtragk`.
6. Clean/Publish server để loại thư viện cũ khỏi bản deploy, rồi Start.

Hoặc build `mvn clean package`, chép `target/kiemtragk.war` vào `webapps` của
Tomcat 10.1 độc lập; đổi HTTP Connector ở `conf/server.xml` sang 8081 trước khi Start.
Các file `db-local.properties` và `mail-local.properties` được giữ nguyên; điền mật
khẩu nếu còn placeholder rồi build/publish lại. Không cần tạo lại database.

| Chức năng | URL |
|---|---|
| Home | http://localhost:8081/kiemtragk/home |
| Login | http://localhost:8081/kiemtragk/login |
| Register | http://localhost:8081/kiemtragk/register |
| Verify OTP | http://localhost:8081/kiemtragk/verify-otp |
| Books admin | http://localhost:8081/kiemtragk/admin/books |
| Authors admin | http://localhost:8081/kiemtragk/admin/authors |

## Tài liệu phiên bản đã đối chiếu

- [SiteMesh 3.2 hỗ trợ Jakarta/Tomcat 10](https://github.com/sitemesh/sitemesh3).
- [Hibernate 6.6 tương thích Jakarta Persistence 3.1](https://hibernate.org/orm/releases/6.6/).
- [Danh sách schema XML Persistence chính thức](https://jakarta.ee/xml/ns/persistence/).
- [Jakarta JSTL 3.0 URI](https://jakarta.ee/specifications/tags/3.0/tagdocs/c/tld-summary).
- [Eclipse Angus Mail](https://eclipse-ee4j.github.io/angus-mail/).

## Danh sách file migration

- `.settings/org.eclipse.jpt.core.prefs`
- `.settings/org.eclipse.wst.common.project.facet.core.xml`
- `README.md`
- `docs/MIGRATION_JAKARTA.md`
- `docs/TEST_REPORT.md`
- `pom.xml`
- `src/main/java/vn/edu/hcmute/kiemtragk/filter/SiteMeshFilter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/config/AppListener_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/config/JpaConfig_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/BaseController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/admin/AuthorAdminController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/admin/BookAdminController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/admin/DashboardController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/auth/LoginController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/auth/LogoutController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/auth/RegisterController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/auth/VerifyOtpController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/web/BookDetailController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/web/HomeController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/controller/web/ReviewController_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/dao/impl/AuthorDAOImpl_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/dao/impl/BaseDAO_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/dao/impl/BookDAOImpl_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/dao/impl/RatingDAOImpl_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/dao/impl/UserDAOImpl_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/Author_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/BookAuthorId_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/BookAuthor_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/Book_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/RatingId_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/Rating_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/User_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/entity/VietnameseTextConverter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/filter/AdminFilter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/filter/AuthenticationFilter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/filter/CsrfFilter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/filter/EncodingFilter_24162019.java`
- `src/main/java/vn/edu/hcmute/kiemtragk/util/EmailUtil_24162019.java`
- `src/main/resources/META-INF/persistence.xml`
- `src/main/webapp/WEB-INF/views/admin/authors/add.jsp`
- `src/main/webapp/WEB-INF/views/admin/authors/edit.jsp`
- `src/main/webapp/WEB-INF/views/admin/authors/list.jsp`
- `src/main/webapp/WEB-INF/views/admin/books/add.jsp`
- `src/main/webapp/WEB-INF/views/admin/books/edit.jsp`
- `src/main/webapp/WEB-INF/views/admin/books/list.jsp`
- `src/main/webapp/WEB-INF/views/admin/dashboard.jsp`
- `src/main/webapp/WEB-INF/views/auth/login.jsp`
- `src/main/webapp/WEB-INF/views/auth/register.jsp`
- `src/main/webapp/WEB-INF/views/auth/verify-otp.jsp`
- `src/main/webapp/WEB-INF/views/error.jsp`
- `src/main/webapp/WEB-INF/views/shared/pagination.jsp`
- `src/main/webapp/WEB-INF/views/web/book-detail.jsp`
- `src/main/webapp/WEB-INF/views/web/home.jsp`
- `src/main/webapp/WEB-INF/web.xml`
- `src/main/webapp/decorators/admin.jsp`
- `src/main/webapp/decorators/web.jsp`
- `src/test/java/vn/edu/hcmute/kiemtragk/BookStoreTest.java`
