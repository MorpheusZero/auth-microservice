package net.snowlynxsoftware.authservice;

/**
 * Application constants
 */
public class AppConstants {
    // Header Constants
    public static final String HEADER_AUTH = "x-auth-token";
    public static final String HEADER_REFRESH = "x-refresh-token";

    // Route Constants
    public static final String ROUTE_API_PREFIX_V1 = "api/v1";

    // Hash Options
    public static final int HASH_ITERATIONS = 257294;
    public static final int HASH_SALT_LENGTH = 32;
    public static final int HASH_KEY_LENGTH = 512;

    // Generic Status Code Response Messages
    public static final String STATUS_CODE_OK_MESSAGE = "Success";
    public static final String STATUS_CODE_BAD_REQUEST_MESSAGE = "There was an error when attempting to perform your request.";
    public static final String STATUS_CODE_UNAUTHORIZED = "UNAUTHORIZED";

    // Other
    public static final int AUTH_CODE_MIN = 10001;
    public static final int AUTH_CODE_MAX = 99999;
}
