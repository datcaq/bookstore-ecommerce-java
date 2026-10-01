# Book Store – Kiểm tra giữa kỳ Lập Trình Web

**Cao Quốc Đạt – MSSV 24162019 – Mã đề 01**

## 1. Công nghệ và chức năng

Maven WAR, Java 17, Tomcat 10.1, Jakarta Servlet 6.0, JSP 3.1, Jakarta JSTL 3.0,
Jakarta Persistence 3.1/Hibernate 6.6.29, SQL Server JDBC, SiteMesh 3.2.1
và Jakarta Mail (Eclipse Angus 2.0.3).
Không sử dụng Spring Boot, Thymeleaf hay frontend framework.

| Câu | Triển khai |
|---|---|
| 1 | MVC + ba tầng; 5 bảng JPA; decorator WEB/ADMIN; footer sinh viên; filter xác thực/phân quyền URL |
| 2 | Đăng ký, OTP Gmail 6 số hết hạn sau 5 phút, đăng nhập bằng session `account`, cập nhật `last_login`, đăng xuất |
| 3 | Home lấy sách từ DB, 6 sách/trang, Previous/số trang/Next, tác giả từ quan hệ, COUNT đánh giá |
| 4 | Chi tiết sách, tác giả, mô tả, danh sách đánh giá; user đăng nhập thêm/cập nhật rating 1–5 |
| 6 | CRUD Books và Authors, phân trang cả hai, chọn nhiều tác giả, xóa an toàn các FK trong transaction |

## 2. Kiến trúc và cấu trúc project

```text
Servlet/Controller -> Service interface -> ServiceImpl
                   -> DAO interface -> DAOImpl -> EntityManager -> SQL Server
Servlet -> JSP (JSTL/EL) -> SiteMesh WEB hoặc ADMIN

database/BookStoreGK_24162019.sql
src/main/java/vn/edu/hcmute/kiemtragk/
  config/          Persistence, cấu hình local, listener đóng EntityManagerFactory
  controller/
    auth/          Register, VerifyOtp, Login, Logout
    web/           Home, BookDetail, Review
    admin/         Dashboard, BookAdmin, AuthorAdmin
  dao/impl/        User, Book, Author, Rating; transaction/pagination tại DAO
  entity/          User, Book, Author, Rating, BookAuthor và khóa ghép
  filter/          Encoding, Authentication, Admin, CSRF, SiteMesh
  service/impl/    Business logic và validation
  util/            Jakarta Mail, password, validation
src/main/resources/
  META-INF/persistence.xml
  db-local.properties.example
  mail-local.properties.example
src/main/webapp/
  WEB-INF/web.xml
  WEB-INF/views/auth/
  WEB-INF/views/web/
  WEB-INF/views/admin/books/     list.jsp, add.jsp, edit.jsp
  WEB-INF/views/admin/authors/   list.jsp, add.jsp, edit.jsp
  decorators/                  web.jsp, admin.jsp
  assets/                      css, js, images, uploads
src/test/java/vn/edu/hcmute/kiemtragk/BookStoreTest.java
docs/TEST_REPORT.md
```

Các class/interface quan trọng đều có hậu tố `_24162019`.
JSP không truy vấn DB và không chứa Java scriptlet. `Book.authors` quản lý ghi
bảng nối; entity `BookAuthor` là mapping chỉ đọc cùng bảng nối, dùng EmbeddedId.

## 3. Tạo database

Yêu cầu **SQL Server 2019 trở lên**. Máy hiện tại đã xác nhận SQL Server 2022
Express (`16.0.1000.6`). Trong SSMS kết nối `localhost\SQLEXPRESS`, mở và chạy:

```text
database/BookStoreGK_24162019.sql
```

Hoặc tại thư mục project, khi Windows Authentication có quyền trên instance:

```powershell
sqlcmd -S '.\SQLEXPRESS' -E -C -b -f 65001 -i database/BookStoreGK_24162019.sql
```

Script tạo database nếu chưa có, `USE BookStoreGK_24162019`, tạo bảng nếu thiếu,
PK/FK và unique email. Không DROP database, không ghi đè bản ghi có sẵn.
Thêm mẫu 12 books, 7 authors, 2 users, 12 liên kết book_author, 3 rating.
Có thể chạy lại mà không nhân đôi dữ liệu mẫu. Nếu database cũ có schema khác
hoặc email trùng, cần kiểm tra lỗi script và đối chiếu đề; script không tự xóa dữ liệu.

**Trạng thái trên máy này:** đã chạy script và kiểm tra chạy lặp thành công.
Database trước đó tồn tại nhưng chưa có bảng; hiện có đủ dữ liệu nêu trên.

### Tiếng Việt và schema của đề

Giữ nguyên `varchar`, `text`, `nvarchar(50)`, `decimal(6,2)`, `phone int` và
`passwd varchar(32)` như đề. Các cột varchar nội dung dùng collation UTF-8.
SQL Server không cho phép UTF-8 ở kiểu legacy `text`; vì vậy `description` và
`review_text` dùng `Vietnamese_CI_AS` (CP1258). `VietnameseTextConverter_24162019`
chuyển dấu tiếng Việt sang dạng tổ hợp khi lưu và NFC khi đọc; dữ liệu seed dùng
cùng cách biểu diễn. Không cần đổi cột sang nvarchar(max).

Giới hạn varchar tính theo byte, nên service kiểm tra cả độ dài UTF-8. Hai cột
text hỗ trợ tiếng Việt/Latin theo CP1258; form báo lỗi đối với emoji hoặc ký tự
không thể lưu. `phone int` không giữ số 0 đầu và chỉ nhận 0–2147483647.
Giá hiển thị đúng số trong DB, không tự nhân/chuyển đơn vị tiền.

## 4. Cấu hình SQL Server

Đã tạo file local được `.gitignore`:

```text
src/main/resources/db-local.properties
```

Nếu thiếu file, sao chép từ `db-local.properties.example`. Nội dung:

```properties
DB_URL=jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=BookStoreGK_24162019;encrypt=true;trustServerCertificate=true
DB_USERNAME=sa
DB_PASSWORD=YOUR_SQL_SERVER_PASSWORD
```

Thay **YOUR_SQL_SERVER_PASSWORD** bằng mật khẩu SQL Server của bạn.
Nếu mật khẩu chứa dấu `\`, phải ghi `\\` theo cú pháp `.properties` hoặc dùng
biến môi trường `DB_PASSWORD`. Biến môi trường cùng tên ưu tiên hơn file local.

Trong SQL Server Configuration Manager, bật TCP/IP cho SQLEXPRESS và khởi động
lại SQL Server nếu vừa thay đổi. Named instance cần SQL Server Browser đang chạy;
có thể thay URL bằng `jdbc:sqlserver://localhost:PORT;databaseName=...` nếu dùng
cổng TCP cố định. Đăng nhập `sa` cần Mixed Mode và tài khoản được bật.
Ứng dụng sử dụng `hibernate.hbm2ddl.auto=validate`, không tự sửa cấu trúc DB.

## 5. Cấu hình Gmail OTP

Điền App Password tại file local được `.gitignore`:

```text
src/main/resources/mail-local.properties
```

```properties
MAIL_USERNAME=datcao19052006@gmail.com
MAIL_APP_PASSWORD=YOUR_GMAIL_APP_PASSWORD
```

Dùng App Password của Gmail tương ứng, không dùng mật khẩu đăng nhập Gmail.
SMTP `smtp.gmail.com:587`, xác thực và STARTTLS bắt buộc. Cũng có thể dùng biến
môi trường `MAIL_USERNAME`, `MAIL_APP_PASSWORD` trước khi khởi động Tomcat.

Luồng: validate đăng ký -> sinh OTP -> gửi mail -> lưu `pendingRegistration`
trong HttpSession -> `/verify-otp` -> OTP đúng mới INSERT user, đặt signup_date,
is_admin=false và xóa pending. Chưa có user trong DB khi OTP sai/hết hạn.
OTP tối đa 5 lần thử; gửi đăng ký lại cách nhau 60 giây. Không thêm bảng OTP
hoặc cột enabled/verified. Nếu gửi mail lỗi, không tạo user và thông báo trên form.

Không đưa file local chứa secret lên Git hoặc chia sẻ WAR đã đóng gói secret.
Các file `.example` chỉ chứa placeholder. WAR chứa bản cấu hình local tại thời
điểm build; sau khi đổi cấu hình trong source cần build và deploy lại.

## 6. Build và kiểm thử

Tại thư mục project:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
mvn clean package
```

Kết quả: `target/kiemtragk.war`. Dự án biên dịch với Java release 17.
Bộ test chạy Hibernate/H2 riêng trong RAM, Tomcat 10.1.60 + Jasper thật và HTTP.
H2 cùng Tomcat embedded chỉ có scope `test`, không có trong WAR triển khai.
SMTP được thay bằng bộ thu mã OTP trong test, không gửi email thật.

```powershell
mvn test
```

Test bao phủ luồng đăng ký/OTP, login, filter, decorator, JSP, phân trang,
review upsert, CRUD, FK, CSRF và XSS output escaping. Chi tiết và các bước cần
kiểm tra với SQL Server/Gmail thật: `docs/TEST_REPORT.md`.

## 7. Deploy lên Tomcat 10.1, port 8081

1. Dùng Tomcat **10.1**, chạy bằng JDK 17. WAR đã chuyển sang Jakarta.
2. Điền hai cấu hình local và chạy `mvn clean package`.
3. Dừng ứng dụng cũ nếu có, chép `target/kiemtragk.war` vào `TOMCAT_HOME/webapps/`.
4. Với Tomcat chạy độc lập, sửa HTTP Connector trong `TOMCAT_HOME/conf/server.xml`
   thành `port="8081"`, rồi khởi động bằng `TOMCAT_HOME/bin/startup.bat`.
5. Mở `http://localhost:8081/kiemtragk/home`.

### Với project hiện tại trong Spring Tools/Eclipse

1. Chọn **KIEM_TRA_GK > Refresh (F5)** rồi **Maven > Update Project (Alt+F5)**.
2. **Properties > Project Facets**: Java **17**, Dynamic Web Module **6.0**, JPA **3.1**.
   Cấu hình local của project đã được cập nhật tương ứng.
3. **Properties > Targeted Runtimes**: chọn **Apache Tomcat v10.1**; runtime dùng JDK 17.
4. Tab **Servers**, nhấp đúp **Tomcat v10.1 Server at localhost**.
5. Mục **Ports**, đổi **HTTP/1.1** từ **8080** sang **8081**, nhấn **Ctrl+S**.
   Cổng thuộc cấu hình server Eclipse ở project `Servers`, không thuộc WAR.
   Cấu hình server hiện tại vẫn dùng 8080; cần thực hiện bước này trước khi Start.
6. Chuột phải server > **Add and Remove…** > thêm **KIEM_TRA_GK** nếu chưa có.
7. **Clean/Publish** server để gỡ thư viện cũ khỏi bản deploy, rồi **Start**.
8. Context root đã xác nhận trong cấu hình project là **`/kiemtragk`**, không phải
   `/KIEM_TRA_GK`. Trong tab Modules của server, Path phải là `/kiemtragk`.

Nếu nhập vào workspace mới: **File > Import > Maven > Existing Maven Projects**,
chọn thư mục này rồi thực hiện các bước trên. Không cần tạo project mới.
Không thêm WAR và cùng project vào một server dưới cùng context root.

Nếu thấy thông báo không kết nối được DB, đọc `TOMCAT_HOME/logs`, kiểm tra database,
mật khẩu, TCP/IP, SQLBrowser và URL; không thay persistence sang H2 khi nộp bài.

## 8. Tài khoản test

| Vai trò | Email | Mật khẩu dữ liệu mẫu |
|---|---|---|
| USER | user@example.com | User@123 |
| ADMIN | admin@example.com | Admin@123 |

Đây là mật khẩu tài khoản mẫu của bài, không phải mật khẩu SQL Server/Gmail.
Password sử dụng PBKDF2-HMAC-SHA256, 210000 vòng: salt 8 byte + hash 16 byte,
Base64 đúng 32 ký tự để vừa schema. Mật khẩu đăng ký không lưu dạng rõ trong session.
Login thành công đổi session ID, lưu `account`, cập nhật `last_login`.
Logout dùng POST và `session.invalidate()`.

## 9. URL kiểm tra

Tiền tố chung: `http://localhost:8081/kiemtragk`

| URL | Chức năng |
|---|---|
| `/` hoặc `/home` | Trang chủ, decorator WEB |
| `/home?page=1`, `/home?page=2` | 6 sách/trang |
| `/book/detail?id=1` | Chi tiết sách, tác giả, review; dùng ID có thật trong DB |
| `/register` | Form đăng ký |
| `/verify-otp` | Form nhập OTP |
| `/login` | Đăng nhập USER/ADMIN |
| `/logout` | POST bằng nút Đăng xuất trên menu |
| `/book/review` | POST form review, lấy userId từ session |
| `/admin`, `/admin/` | Dashboard, decorator ADMIN |
| `/admin/books`, `/admin/books?page=2` | Danh sách và phân trang Books |
| `/admin/books/add` | GET form / POST thêm |
| `/admin/books/edit?id=1` | GET form sửa; POST `/admin/books/edit` |
| `/admin/books/delete` | POST từ nút Xóa |
| `/admin/authors`, `/admin/authors?page=2` | Danh sách và phân trang Authors |
| `/admin/authors/add` | GET form / POST thêm |
| `/admin/authors/edit?id=1` | GET form sửa; POST `/admin/authors/edit` |
| `/admin/authors/delete` | POST từ nút Xóa |

URL admin được chặn bằng filter: khách chuyển login; USER nhận HTTP 403.
Không chỉ ẩn menu. Các POST kiểm tra CSRF token từ form, vì vậy dùng nút/form
trên website khi test, không gọi URL xóa bằng thanh địa chỉ.

Ảnh bìa: nhập URL http/https hoặc tên file có sẵn trong
`src/main/webapp/assets/uploads/`. Không có chức năng upload nhị phân trong đề.
Ảnh trống/sai/không tải được dùng ảnh mặc định local; seed để ảnh mặc định.

## 10. Việc còn cần làm thủ công và ảnh nộp bài

Điền SQL Server password và Gmail App Password; build/deploy lại; thực hiện đăng ký
bằng email nhận được mail để xác nhận luồng SMTP thật. Chụp ảnh sau khi chạy trên
SQL Server thật, không dùng dữ liệu H2 kiểm thử làm bằng chứng database triển khai.

Các ảnh nên chụp:

- Cấu trúc project Controller/Service/DAO/Entity và hậu tố MSSV.
- Database diagram đủ 5 bảng, PK/FK và dữ liệu mẫu.
- Register; email chứa OTP; Verify OTP sai/đúng; Login.
- Home 6 sách, trang 1 và trang 2, header/footer decorator WEB.
- Chi tiết sách có tác giả, số lượng review, danh sách review và form thêm review.
- Sau khi gửi review; gửi lại cho thấy cập nhật và count không tăng trùng.
- USER bị chặn ở `/admin/books` và không thấy menu Trang quản trị.
- Dashboard, menu/footer decorator ADMIN.
- Books: danh sách phân trang, form thêm nhiều tác giả, sửa, xóa.
- Authors: danh sách trang 1/2, thêm, sửa, xóa.
- Terminal `mvn clean package` có **BUILD SUCCESS**.

## 11. Tài liệu tham khảo kỹ thuật

- [SiteMesh 3: cấu hình filter/decorator](https://sitemesh.github.io/sitemesh-website/configuration.html).
- [SiteMesh: nhánh 3.2 cho Jakarta/Tomcat 10](https://github.com/sitemesh/sitemesh3).
- [Hibernate 6.6 và Jakarta Persistence 3.1](https://hibernate.org/orm/releases/6.6/).
- [Jakarta JSTL: URI jakarta.tags.core](https://jakarta.ee/specifications/tags/3.0/tagdocs/c/tld-summary).
- [SQL Server: kiểu chuỗi và hỗ trợ UTF-8](https://learn.microsoft.com/en-us/sql/t-sql/data-types/string-and-binary-types?view=sql-server-ver17).
- [Microsoft: mã CP1258 tiếng Việt](https://learn.microsoft.com/en-us/windows/win32/intl/code-page-identifiers).

Chi tiết migration, danh sách file và kết quả kiểm tra: `docs/MIGRATION_JAKARTA.md`.
Các namespace Servlet/Persistence/Mail trong source đều là `jakarta.*`.
`javax.crypto` vẫn giữ nguyên vì đây là thư viện Java SE dùng băm mật khẩu.
