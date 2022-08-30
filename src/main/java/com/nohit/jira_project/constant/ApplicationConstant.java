package com.nohit.jira_project.constant;

public class ApplicationConstant {


    // Timelife of Token
    public static final long EXPIRATION_TIME = 60 * 60 * 1000;
    public static final String SECRET_KEY = "secret";

    public static final int DEFAULT_STATUS = 1;
    public static final int DEFAULT_ROLE = 3;
    public static final String DEFAULT_AVATAR = "0.jpg";

    public class Role {
        // public static final String ADMIN = "ADMIN";
        // public static final String LEADER = "LEADER";
        // public static final String MEMBER = "MEMBER";
        public static final String CLIENT = "CLIENT";
    }

}
