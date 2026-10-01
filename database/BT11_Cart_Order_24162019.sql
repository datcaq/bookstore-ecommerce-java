USE BookStoreGK_24162019;
GO

-- 1. Tao bang cart neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'cart')
BEGIN
    CREATE TABLE cart (
        cart_id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT NOT NULL UNIQUE,
        created_at DATETIME NULL,
        updated_at DATETIME NULL,
        CONSTRAINT FK_cart_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );
END
GO

-- 2. Tao bang cart_item neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'cart_item')
BEGIN
    CREATE TABLE cart_item (
        cart_id INT NOT NULL,
        book_id INT NOT NULL,
        quantity INT NOT NULL CHECK (quantity > 0),
        CONSTRAINT PK_cart_item PRIMARY KEY (cart_id, book_id),
        CONSTRAINT FK_cart_item_cart FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
        CONSTRAINT FK_cart_item_books FOREIGN KEY (book_id) REFERENCES books(bookid) ON DELETE CASCADE
    );
END
GO

-- 3. Tao bang orders neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'orders')
BEGIN
    CREATE TABLE orders (
        order_id INT IDENTITY(1,1) PRIMARY KEY,
        user_id INT NOT NULL,
        order_date DATETIME NOT NULL,
        total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
        payment_method VARCHAR(50) NOT NULL DEFAULT 'COD',
        status VARCHAR(50) NOT NULL DEFAULT 'NEW',
        receiver_name NVARCHAR(100) NOT NULL,
        receiver_phone VARCHAR(20) NOT NULL,
        shipping_address NVARCHAR(255) NOT NULL,
        CONSTRAINT FK_orders_users FOREIGN KEY (user_id) REFERENCES users(id)
    );
END
GO

-- 4. Tao bang order_item neu chua ton tai
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'order_item')
BEGIN
    CREATE TABLE order_item (
        order_id INT NOT NULL,
        book_id INT NOT NULL,
        quantity INT NOT NULL CHECK (quantity > 0),
        unit_price DECIMAL(6, 2) NOT NULL,
        subtotal DECIMAL(10, 2) NOT NULL,
        CONSTRAINT PK_order_item PRIMARY KEY (order_id, book_id),
        CONSTRAINT FK_order_item_orders FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
        CONSTRAINT FK_order_item_books FOREIGN KEY (book_id) REFERENCES books(bookid)
    );
END
GO
