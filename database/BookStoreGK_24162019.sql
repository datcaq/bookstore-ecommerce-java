-- Cao Quốc Đạt - 24162019 - Mã đề 01
-- SQL Server 2019+; UTF-8 collation giữ nguyên varchar/text của đề và lưu tiếng Việt.
-- Chạy bằng SSMS hoặc sqlcmd. Không xóa hay cập nhật dữ liệu đã có.
IF DB_ID(N'BookStoreGK_24162019') IS NULL
    EXEC(N'CREATE DATABASE BookStoreGK_24162019 COLLATE Latin1_General_100_CI_AS_SC_UTF8');
GO
USE BookStoreGK_24162019;
GO
SET XACT_ABORT ON;
BEGIN TRANSACTION;
IF OBJECT_ID(N'dbo.users', N'U') IS NULL
CREATE TABLE dbo.users (
 id int IDENTITY(1,1) NOT NULL CONSTRAINT PK_users PRIMARY KEY,
 email varchar(50) COLLATE Latin1_General_100_CI_AS_SC_UTF8 NOT NULL,
 fullname nvarchar(50) NULL,
 phone int NULL,
 passwd varchar(32) NOT NULL,
 signup_date datetime NULL,
 last_login datetime NULL,
 is_admin bit NULL
);
IF NOT EXISTS(SELECT 1 FROM sys.indexes WHERE object_id=OBJECT_ID(N'dbo.users') AND name=N'UX_users_email')
 CREATE UNIQUE INDEX UX_users_email ON dbo.users(email);
IF OBJECT_ID(N'dbo.books', N'U') IS NULL
CREATE TABLE dbo.books (
 bookid int IDENTITY(1,1) NOT NULL CONSTRAINT PK_books PRIMARY KEY,
 isbn int NULL,
 title varchar(200) COLLATE Latin1_General_100_CI_AS_SC_UTF8 NULL,
 publisher varchar(100) COLLATE Latin1_General_100_CI_AS_SC_UTF8 NULL,
 price decimal(6,2) NULL,
 description text COLLATE Vietnamese_CI_AS NULL,
 publish_date date NULL,
 cover_image varchar(100) COLLATE Latin1_General_100_CI_AS_SC_UTF8 NULL,
 quantity int NULL
);
IF OBJECT_ID(N'dbo.author', N'U') IS NULL
CREATE TABLE dbo.author (
 author_id int IDENTITY(1,1) NOT NULL CONSTRAINT PK_author PRIMARY KEY,
 author_name varchar(100) COLLATE Latin1_General_100_CI_AS_SC_UTF8 NULL,
 date_of_birth date NULL
);
IF OBJECT_ID(N'dbo.book_author', N'U') IS NULL
CREATE TABLE dbo.book_author (
 bookid int NOT NULL, author_id int NOT NULL,
 CONSTRAINT PK_book_author PRIMARY KEY(bookid,author_id),
 CONSTRAINT FK_book_author_books FOREIGN KEY(bookid) REFERENCES dbo.books(bookid),
 CONSTRAINT FK_book_author_author FOREIGN KEY(author_id) REFERENCES dbo.author(author_id)
);
IF OBJECT_ID(N'dbo.rating', N'U') IS NULL
CREATE TABLE dbo.rating (
 userid int NOT NULL, bookid int NOT NULL,
 rating tinyint NULL,
 review_text text COLLATE Vietnamese_CI_AS NULL,
 CONSTRAINT PK_rating PRIMARY KEY(userid,bookid),
 CONSTRAINT FK_rating_users FOREIGN KEY(userid) REFERENCES dbo.users(id),
 CONSTRAINT FK_rating_books FOREIGN KEY(bookid) REFERENCES dbo.books(bookid),
 CONSTRAINT CK_rating_range CHECK(rating BETWEEN 1 AND 5)
);
IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email='user@example.com')
 INSERT dbo.users(email,fullname,phone,passwd,signup_date,is_admin) VALUES('user@example.com',N'Nguyễn Văn An',912345678,'R0sxOXVzcjFPAHNgELsCn78R0qOyeg3k',GETDATE(),0);
IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email='admin@example.com')
 INSERT dbo.users(email,fullname,phone,passwd,signup_date,is_admin) VALUES('admin@example.com',N'Cao Quốc Đạt',912345678,'R0sxOWFkbTHjB+wWZqToyKfkX09fwCM7',GETDATE(),1);
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Nguyễn Nhật Ánh')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Nguyễn Nhật Ánh','19550507');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Tô Hoài')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Tô Hoài','19200927');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Nam Cao')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Nam Cao','19171029');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Vũ Trọng Phụng')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Vũ Trọng Phụng','19121020');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Thạch Lam')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Thạch Lam','19100707');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Ngô Tất Tố')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Ngô Tất Tố','18940101');
IF NOT EXISTS(SELECT 1 FROM dbo.author WHERE author_name=N'Nguyễn Tuân')
 INSERT dbo.author(author_name,date_of_birth) VALUES(N'Nguyễn Tuân','19100710');
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162001)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162001,N'Mắt biếc',N'Nhà xuất bản Văn học',70.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240101',NULL,11);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162001 AND a.author_name=N'Nguyễn Nhật Ánh'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162002)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162002,N'Tôi thấy hoa vàng trên cỏ xanh',N'Nhà xuất bản Văn học',75.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240102',NULL,12);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162002 AND a.author_name=N'Nguyễn Nhật Ánh'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162003)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162003,N'Cho tôi xin một vé đi tuổi thơ',N'Nhà xuất bản Văn học',80.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240103',NULL,13);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162003 AND a.author_name=N'Nguyễn Nhật Ánh'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162004)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162004,N'Cô gái đến từ hôm qua',N'Nhà xuất bản Văn học',85.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240104',NULL,14);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162004 AND a.author_name=N'Nguyễn Nhật Ánh'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162005)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162005,N'Dế Mèn phiêu lưu ký',N'Nhà xuất bản Văn học',90.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240105',NULL,15);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162005 AND a.author_name=N'Tô Hoài'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162006)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162006,N'Vợ chồng A Phủ',N'Nhà xuất bản Văn học',95.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240106',NULL,16);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162006 AND a.author_name=N'Tô Hoài'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162007)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162007,N'Chí Phèo',N'Nhà xuất bản Văn học',100.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240107',NULL,17);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162007 AND a.author_name=N'Nam Cao'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162008)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162008,N'Lão Hạc',N'Nhà xuất bản Văn học',105.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240108',NULL,18);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162008 AND a.author_name=N'Nam Cao'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162009)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162009,N'Số đỏ',N'Nhà xuất bản Văn học',110.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240109',NULL,19);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162009 AND a.author_name=N'Vũ Trọng Phụng'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162010)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162010,N'Hà Nội băm sáu phố phường',N'Nhà xuất bản Văn học',115.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240110',NULL,20);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162010 AND a.author_name=N'Thạch Lam'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162011)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162011,N'Tắt đèn',N'Nhà xuất bản Văn học',120.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240111',NULL,21);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162011 AND a.author_name=N'Ngô Tất Tố'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
IF NOT EXISTS(SELECT 1 FROM dbo.books WHERE isbn=24162012)
 INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
 VALUES(24162012,N'Vang bóng một thời',N'Nhà xuất bản Văn học',125.00,N'Một tác phẩm văn học Việt Nam giàu cảm xúc, mang đến những góc nhìn về con người và cuộc sống. Dữ liệu minh họa phục vụ bài kiểm tra.', '20240112',NULL,22);
INSERT dbo.book_author(bookid,author_id)
 SELECT b.bookid,a.author_id FROM dbo.books b CROSS JOIN dbo.author a
 WHERE b.isbn=24162012 AND a.author_name=N'Nguyễn Tuân'
 AND NOT EXISTS(SELECT 1 FROM dbo.book_author ba WHERE ba.bookid=b.bookid AND ba.author_id=a.author_id);
INSERT dbo.rating(userid,bookid,rating,review_text)
 SELECT u.id,b.bookid,5,N'Sách rất hay, câu chuyện nhẹ nhàng và sâu sắc.' FROM dbo.users u CROSS JOIN dbo.books b
 WHERE u.email='user@example.com' AND b.isbn=24162001
 AND NOT EXISTS(SELECT 1 FROM dbo.rating r WHERE r.userid=u.id AND r.bookid=b.bookid);
INSERT dbo.rating(userid,bookid,rating,review_text)
 SELECT u.id,b.bookid,4,N'Một tác phẩm đáng đọc trong tủ sách văn học Việt Nam.' FROM dbo.users u CROSS JOIN dbo.books b
 WHERE u.email='admin@example.com' AND b.isbn=24162001
 AND NOT EXISTS(SELECT 1 FROM dbo.rating r WHERE r.userid=u.id AND r.bookid=b.bookid);
INSERT dbo.rating(userid,bookid,rating,review_text)
 SELECT u.id,b.bookid,5,N'Câu chuyện tuổi thơ sinh động và đầy ý nghĩa.' FROM dbo.users u CROSS JOIN dbo.books b
 WHERE u.email='user@example.com' AND b.isbn=24162005
 AND NOT EXISTS(SELECT 1 FROM dbo.rating r WHERE r.userid=u.id AND r.bookid=b.bookid);
COMMIT TRANSACTION;
GO
SELECT 'users' AS table_name,COUNT(*) AS row_count FROM dbo.users
UNION ALL SELECT 'books',COUNT(*) FROM dbo.books
UNION ALL SELECT 'author',COUNT(*) FROM dbo.author
UNION ALL SELECT 'book_author',COUNT(*) FROM dbo.book_author
UNION ALL SELECT 'rating',COUNT(*) FROM dbo.rating;
