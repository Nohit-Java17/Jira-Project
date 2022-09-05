package com.nohit.jira_project.constant;

public class ApplicationConstant {
    public static final long EXPIRATION_TIME = 60 * 60 * 1000;
    public static final String SECRET_KEY = "secret";

    public static final String DEFAULT_ROLE = "client";
    public static final String DEFAULT_STATUS = "Đang giao";
    public static final String DEFAULT_PAYMENT = "Tiền mặt";
    public static final int DEFAULT_PROVINCE = 29;

    public class Role {
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
    }

    public class Payment {
        public static final String TRANSFER = "Chuyển khoản";
        public static final String CARD = "Thẻ tín dụng";
        public static final String CASH = "Tiền mặt";
    }

    public class Status {
        public static final String PREPARED = "Đang giao";
        public static final String RECEIVED = "Đã giao";
        public static final String CANCELLED = "Đã hủy";
    }

    public class Category {
        public static final String LAPTOP = "Máy tính xách tay";
        public static final String COMPUTER = "Máy tính để bàn";
        public static final String SMART_PHONE = "Điện thoại di động";
        public static final String TABLET = "Máy tính bảng";
        public static final String DEVICES = "Thiết bị ngoại vi";
    }
}
