# Báo cáo kiểm thử – Cao Quốc Đạt 24162019

## Kết quả cuối

Ngày 24/09/2026, chạy `mvn clean package` bằng JDK 17: **BUILD SUCCESS**.
Sau migration Jakarta: **15 tests, 0 failures, 0 errors, 0 skipped**. WAR: `target/kiemtragk.war`.
Log lệnh cuối: `tmp/jakarta-final-build.log`. Chức năng mail trong test dùng bộ thu OTP,
không phải bằng chứng gửi Gmail thật.

## Môi trường

- Windows, Maven 3.9.11, JDK 17, Tomcat embedded 10.1.60, Hibernate 6.6.29.Final.
- HTTP integration dùng H2 trong RAM; không cần SQL Server password hay SMTP secret.
- Script SQL đã chạy trực tiếp trên `localhost\SQLEXPRESS`, SQL Server 2022.
- Gmail thật chưa kiểm tra trong migration; giữ nguyên cấu hình local của người dùng.
- Ứng dụng JDBC với tài khoản `sa` thật chưa kiểm tra trong migration; cần cấu hình local.

## Đối chiếu 21 test yêu cầu

Các test tự động ở `BookStoreTest.java`. Một phương thức kiểm tra nhiều bước liên tiếp.

| STT | Nội dung | Phương thức / kiểm tra |
|---|---|---|
| 1 | Đăng ký email mới | registerWrongThenCorrectOtpOverHttp; POST thực, service/DAO thực, mail test |
| 2 | OTP sai không tạo user | Kiểm tra DB chưa có user sau OTP sai |
| 3 | OTP đúng tạo user | Kiểm tra INSERT, signup_date, is_admin=false, xóa pending session |
| 4 | Login sai | invalidLogin; thông báo form, không redirect thành công |
| 5 | Login USER vào home | userLoginAndAdminProtection; session và last_login |
| 6 | USER bị chặn admin | HTTP 403 tại books và authors/add |
| 7 | Login ADMIN | adminDecoratorAndDashboard; đúng decorator và footer |
| 8 | Home 6 sách | homePaginationAndWebDecorator; đếm 6 card |
| 9 | Home trang 2 | Kiểm tra sách trang 2 khác trang 1 |
| 10 | Link title -> detail | Kiểm tra href có book ID thật và HTTP detail 200 |
| 11 | Detail authors/reviews | detailAndReviewUpsert; render quan hệ từ DB |
| 12 | USER thêm review | POST review có session, userid giả trong form không có hiệu lực |
| 13 | Review count | COUNT tăng lần đầu, gửi lại chỉ UPDATE, rating đổi |
| 14 | Create Book | bookCrudAndRelations; chọn 2 authors, đếm tăng |
| 15 | Update Book | Sửa giá và bỏ 1 author, đọc lại DB để đối chiếu |
| 16 | Delete Book | Xóa sách có rating và book_author; không lỗi FK |
| 17 | Books pagination | bookListPagination; HTTP trang 2 |
| 18 | Create Author | authorCrudAndPagination; đếm tăng |
| 19 | Update Author | Đổi tên/ngày sinh, đọc lại DB |
| 20 | Delete Author | Xóa tác giả đang gắn sách, sách còn và liên kết được xóa |
| 21 | Authors pagination | 7 tác giả để có trang 2, kiểm tra nội dung đúng trang |

Kiểm tra bổ sung: OTP hết hạn và giới hạn 5 lần thử, duplicate email, đăng ký sai
định dạng/xác nhận mật khẩu, rating ngoài 1–5, review rỗng, anonymous bị chuyển login,
CSRF, logout vô hiệu session, ID sai/không tồn tại, escape XSS review, băm mật khẩu
đúng 32 ký tự và salt ngẫu nhiên, chuyển đổi dấu tiếng Việt.

Test thứ 15 xác nhận SMTP provider của Eclipse Angus/Jakarta Mail nạp được và
MIME giữ đúng tiêu đề/nội dung tiếng Việt sau khi ghi/đọc. Không kết nối SMTP thật.

## Kiểm tra SQL Server đã thực hiện

1. Đọc version và schema: database có sẵn, ban đầu chưa có bảng.
2. Chạy script tạo bảng, PK/FK và seed thành công.
3. Chạy lại cùng script: số lượng không đổi (2 users, 12 books, 7 authors,
   12 book_author, 3 rating).
4. JOIN books/book_author/author đọc được `Mắt biếc` và `Nguyễn Nhật Ánh`.
5. Đọc review_text từ SQL Server giữ được dấu tiếng Việt dạng tổ hợp.

SQL Server từ chối UTF-8 cho `text`; script cuối dùng Vietnamese_CI_AS cho hai cột
legacy text và UTF-8 cho varchar. Lần thử ban đầu bị lỗi đã được sửa trước khi seed.

## Cần xác nhận thủ công sau khi nhập cấu hình

Trước migration đã kiểm tra trực quan bằng trình duyệt trên phiên H2 riêng.
Sau migration, test HTTP trên Tomcat 10.1/Jasper xác nhận các trang, JSTL và hai
decorator vẫn render thành công. SiteMesh 3.2 cần đặt tiền tố decorator rỗng;
lỗi đường dẫn 404 ở lần chạy đầu đã được sửa.

1. Build WAR và deploy Tomcat 10.1, cổng 8081 theo README.
2. Login hai tài khoản mẫu với SQL Server thật; kiểm tra `last_login` trong SSMS.
3. Đăng ký email có thật, nhận Gmail OTP, nhập sai rồi đúng; đối chiếu users.
4. Test lại home, review và CRUD bằng trình duyệt trên SQL Server thật.
5. Chụp ảnh các màn hình trong checklist README.

Kết quả tự động được Maven ghi tại `target/surefire-reports/`; không dùng báo cáo
H2 để khẳng định SMTP thật hoặc kết nối JDBC bằng mật khẩu cá nhân đã hoạt động.
