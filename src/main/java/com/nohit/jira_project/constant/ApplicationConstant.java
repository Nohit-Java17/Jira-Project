package com.nohit.jira_project.constant;

import java.util.*;

import static java.util.Map.*;

public class ApplicationConstant {
    public static final long EXPIRATION_TIME = 60 * 60 * 1000;
    public static final String SECRET_KEY = "secret";

    public static final String DEFAULT_ROLE = "client";
    public static final String DEFAULT_STATUS = "Đang giao";
    public static final int DEFAULT_PROVINCE = 29;
    public static final int DEFAULT_CATEGORY = 0;
    public static final int DEFAULT_SIZE_PAGE = 8;

    public class Role {
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
    }

    public class Payment {
        public static final String TRANSFER = "Chuyển khoản";
        public static final String CARD = "Qua thẻ";
        public static final String CASH = "Tiền mặt";
    }

    public class Status {
        public static final String PREPARED = "Đang giao";
        public static final String RECEIVED = "Đã giao";
        public static final String CANCELLED = "Đã hủy";
    }

    public class ChoosenOne {
        public static final String TRANG_CHU = "Trang chủ";
        public static final String SAN_PHAM = "Sản phẩm";
        public static final String PHAN_LOAI = "Phân loại";
        public static final String CHI_TIET = "Chi tiết";
        public static final String GIO_HANG = "Giỏ hàng";
        public static final String THANH_TOAN = "Thanh toán";
        public static final String GIOI_THIEU = "Giới thiệu";
        public static final String LIEN_HE = "Liên hệ";
        public static final String CA_NHAN = "Thông tin cá nhân";
        public static final String LICH_SU = "Lịch sử mua hàng";
    }

    public static final Map<String, Integer> CATEGORIES_MAP = of(
            "Tấc cả", 0,
            "Máy tính xách tay", 1,
            "Máy tính để bàn", 2,
            "Máy tính bảng", 3,
            "Điện thoại di động", 4,
            "Thiết bị ngoại vi", 5);
    public static final Map<String, Integer> COUPON_MAP = of(
            "nohit", 10000,
            "cybersoft", 100000);
}
