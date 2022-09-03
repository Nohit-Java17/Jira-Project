package com.nohit.jira_project.constant;

public class ApplicationConstant {
    public static final long EXPIRATION_TIME = 60 * 60 * 1000;
    public static final String SECRET_KEY = "secret";

    public static final int DEFAULT_STATUS = 0;
    public static final int DEFAULT_PAYMENT = 2;
    public static final int DEFAULT_ROLE = 1;

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
}
