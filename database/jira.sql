-- create db

CREATE DATABASE IF NOT EXISTS jira_project;

-- use db

USE jira_project;

-- create table khach_hang

CREATE TABLE
    IF NOT EXISTS khach_hang(
        ma INT NOT NULL AUTO_INCREMENT,
        thu_dien_tu NVARCHAR(50) NOT NULL,
        mat_khau NVARCHAR(255) NOT NULL,
        ho_ten NVARCHAR(50),
        hinh_anh NVARCHAR(10) NOT NULL,
        so_dien_thoai NVARCHAR(20),
        dia_chi NVARCHAR(100),
        xa_phuong NVARCHAR(50),
        huyen_quan NVARCHAR(50),
        tinh_thanh NVARCHAR(50),
        vai_tro NVARCHAR(10),
        PRIMARY KEY (ma)
    );

-- create table san_pham

CREATE TABLE
    IF NOT EXISTS san_pham(
        ma INT NOT NULL AUTO_INCREMENT,
        ten NVARCHAR(100) NOT NULL,
        album NVARCHAR(10) NOT NULL,
        mo_ta TEXT,
        gia_goc INT NOT NULL,
        giam_gia INT,
        so_luong INT NOT NULL,
        ngay_nhap DATE NOT NULL,
        ton_kho INT NOT NULL,
        danh_gia INT NOT NULL,
        phan_loai NVARCHAR(50) NOT NULL,
        thuong_hieu NVARCHAR(20) NOT NULL,
        PRIMARY KEY (ma)
    );

-- create table nguoi_nhan

CREATE TABLE
    IF NOT EXISTS nguoi_nhan(
        ma INT NOT NULL AUTO_INCREMENT,
        ho_ten NVARCHAR(50) NOT NULL,
        so_dien_thoai NVARCHAR(20) NOT NULL,
        dia_chi NVARCHAR(100) NOT NULL,
        xa_phuong NVARCHAR(50) NOT NULL,
        huyen_quan NVARCHAR(50) NOT NULL,
        tinh_thanh NVARCHAR(50) NOT NULL,
        ghi_chu TEXT,
        PRIMARY KEY (ma)
    );

-- create table don_hang

CREATE TABLE
    IF NOT EXISTS don_hang(
        ma INT NOT NULL AUTO_INCREMENT,
        ngay_dat DATE NOT NULL,
        ngay_giao DATE,
        tong_gio_hang INT NOT NULL,
        chi_phi_van_chuyen INT NOT NULL,
        tong_don_hang INT NOT NULL,
        phuong_thuc_thanh_toan NVARCHAR(50) NOT NULL,
        trang_thai NVARCHAR(20) NOT NULL,
        ma_khach_hang INT NOT NULL,
        ma_nguoi_nhan INT NOT NULL,
        PRIMARY KEY (ma),
        FOREIGN KEY (ma_khach_hang) REFERENCES khach_hang(ma),
        FOREIGN KEY (ma_nguoi_nhan) REFERENCES nguoi_nhan(ma)
    );

-- create table chi_tiet_don_hang

CREATE TABLE
    IF NOT EXISTS chi_tiet_don_hang(
        ma_don_hang INT NOT NULL,
        ma_san_pham INT NOT NULL,
        so_luong_san_pham INT NOT NULL,
        gia_ban INT NOT NULL,
        PRIMARY KEY (ma_don_hang, ma_san_pham),
        FOREIGN KEY (ma_don_hang) REFERENCES don_hang(ma),
        FOREIGN KEY (ma_san_pham) REFERENCES san_pham(ma)
    );

-- create table phi_van_chuyen

CREATE TABLE
    IF NOT EXISTS phi_van_chuyen(
        ma INT NOT NULL AUTO_INCREMENT,
        tinh_thanh NVARCHAR(50) NOT NULL,
        chi_phi_van_chuyen INT NOT NULL,
        PRIMARY KEY (ma)
    );

-- create table thu_phan_hoi

CREATE TABLE
    IF NOT EXISTS thu_phan_hoi(
        ma INT NOT NULL AUTO_INCREMENT,
        ho_ten NVARCHAR(50) NOT NULL,
        email NVARCHAR(50) NOT NULL,
        chu_de NVARCHAR(20),
        noi_dung TEXT,
        PRIMARY KEY (ma)
    );

-- create table nhan_xet

CREATE TABLE
    IF NOT EXISTS nhan_xet(
        ma INT NOT NULL AUTO_INCREMENT,
        danh_gia INT NOT NULL,
        binh_luan TEXT,
        ma_san_pham INT NOT NULL,
        PRIMARY KEY (ma),
        FOREIGN KEY (ma_san_pham) REFERENCES san_pham(ma)
    );

-- add data to khach_hang

INSERT INTO
    khach_hang (
        thu_dien_tu,
        mat_khau,
        ho_ten,
        hinh_anh,
        so_dien_thoai,
        dia_chi,
        xa_phuong,
        huyen_quan,
        tinh_thanh,
        vai_tro
    )
VALUES (
        'nguyenvana@gmail.com',
        '$2a$12$bIlMeYu4wYSQBQY/IDYfeODaLw47qaoJRMcNukFjV0nqFppUSd9Ue',
        'Nguyễn Văn A',
        '1.jpg',
        '0987654321',
        '112 đường Cao Thắng',
        'P.4',
        'Q.3',
        'Hồ Chí Minh',
        'client'
    );

-- add data to san_pham

INSERT INTO
    san_pham(
        ten,
        album,
        mo_ta,
        gia_goc,
        giam_gia,
        so_luong,
        ngay_nhap,
        ton_kho,
        danh_gia,
        phan_loai,
        thuong_hieu
    )
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    ),
VALUES (
        'Máy tính xách tay Apple MacBook Pro MR9R2',
        '1808315',
        '- CPU: Core i5 (2.3 GHz)
- Màn hình: 13.3"" (2560 x 1600), không cảm ứng
- RAM: 8GB LPDDR3 2133MHz
- Đồ họa: Intel Iris Plus Graphics 650
- Lưu trữ: 512GB SSD
- Hệ điều hành: macOS
- Pin: Pin liền',
        49890000,
        2100000,
        10,
        1,
        5,
        'Máy tính xách tay',
        'APPLE'
    );

-- add data to phi_van_chuyen

INSERT INTO
    phi_van_chuyen (
        tinh_thanh,
        chi_phi_van_chuyen
    )
VALUES ('An Giang', 20000), ('Bạc Liêu', 27000), ('Bắc Giang', 44000), ('Bắc Kạn', 47000), ('Bắc Ninh', 54000), ('Bến Tre', 16000), ('Bình Dương', 5000), ('Bình Định', 15000), ('Bình Phước', 3000), ('Bình Thuận', 7000), ('Cà Mau', 28000), ('Cao Bằng', 49000), ('Cần Thơ', 24000), ('Đà Nẵng', 20000), ('Đắk Lắk', 6000), ('Đắk Nông', 5000), ('Điện Biên', 38000), ('Đồng Nai', 6000), ('Đồng Tháp', 11000), ('Gia Lai', 8000), ('Hà Giang', 50000), ('Hà Nam', 52000), ('Hà Nội', 56000), ('Hà Tĩnh', 24000), ('Hải Dương', 67000), ('Hải Phòng', 65000), ('Hậu Giang', 25000), ('Hòa Bình', 34000), ('Hồ Chí Minh', 0), ('Hưng Yên', 53000), ('Khánh Hòa', 13000), ('Kiên Giang', 21000), ('Kon Tum', 10000), ('Lai Châu', 40000), ('Lạng Sơn', 45000), ('Lào Cai', 39000), ('Lâm Đồng', 4000), ('Long An', 12000), ('Nam Định', 63000), ('Nghệ An', 27000), ('Ninh Bình', 62000), ('Ninh Thuận', 11000), ('Phú Thọ', 35000), ('Phú Yên', 14000), ('Quảng Bình', 23000), ('Quảng Nam', 18000), ('Quảng Ngãi', 16000), ('Quảng Ninh', 68000), ('Quảng Trị', 22000), ('Sóc Trăng', 26000), ('Sơn La', 36000), ('Tây Ninh', 10000), ('Thái Bình', 64000), ('Thái Nguyên', 46000), ('Thanh Hóa', 28000), ('Thừa Thiên Huế', 21000), ('Tiền Giang', 14000), ('Trà Vinh', 17000), ('Tuyên Quang', 48000), ('Vĩnh Long', 15000), ('Vĩnh Phúc', 55000), ('Vũng Tàu', 8000), ('Yên Bái', 37000);