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
    public static final int DEFAULT_PRODUCT = 0;
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

    public static final Map<String, Integer> CATEGORIES_MAP = of(
            "Tất cả", 0,
            "Máy tính xách tay", 1,
            "Máy tính để bàn", 2,
            "Máy tính bảng", 3,
            "Điện thoại di động", 4,
            "Thiết bị ngoại vi", 5);

    public static final Map<String, Integer> PRODUCTS_MAP = of(
            "Tất cả", 0,
            "Bán chạy", 1,
            "Mới nhất", 2,
            "Khuyến mãi", 3,
            "Giá tăng dần", 4,
            "Giá giảm dần", 5);

    public static final Map<String, Integer> COUPON_MAP = of(
            "nohit", 10000,
            "cybersoft", 100000);
}
