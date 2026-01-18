package utils;

public class Config {
    // Browser paths (Windows)
    public static final String YANDEX_BROWSER_PATH =
            "C:\\Users\\chuma\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

    // URL адреса
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";
    public static final String API_URL = BASE_URL + "/api";

    // API endpoints
    public static final String REGISTER_ENDPOINT = "/auth/register";
    public static final String LOGIN_ENDPOINT = "/auth/login";
    public static final String USER_ENDPOINT = "/auth/user";

    // URL страниц
    public static final String LOGIN_PAGE = BASE_URL + "/login";
    public static final String REGISTER_PAGE = BASE_URL + "/register";
    public static final String FORGOT_PASSWORD_PAGE = BASE_URL + "/forgot-password";
    public static final String MAIN_PAGE = BASE_URL + "/";

    // Timeouts
    public static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // Test data constants
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final String VALID_PASSWORD_SUFFIX = "123";

    // Browser names
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_YANDEX = "yandex";

        // Headers
    public static final String CONTENT_TYPE_HEADER = "Content-type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String AUTHORIZATION_HEADER = "Authorization";


}