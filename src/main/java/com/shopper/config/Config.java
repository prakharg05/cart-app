package com.shopper.config;

public class Config {

    public static final String ADMIN_DOMAIN = "shopper.com";



    public static final String ADMIN_ROLE = "ADMIN";

    public static final String USER_ROLE = "USER";

    public static final String EMAIL_SEPERATOR = "@";

    public static class ErrorCode {
        public static int ERROR_INVALID_PRODUCT_ID = 4002;
        public static int ERROR_INVALID_INPUT = 4001;
        public static int ERROR_CART_ITEM_MISSING = 4004;
        public static int ERROR_NOT_ENOUGH_INVENTORY = 4003;
        public static int ERROR_INTERNAL_SERVER_FAILURE = 5001;
    }
}
