package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import pages.MainPage;
import pages.AuthPage;
import models.User;
import utils.WebDriverFactory;
import utils.Config;
import com.google.gson.Gson;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected MainPage mainPage;
    protected AuthPage authPage;
    protected Gson gson = new Gson();

    @Before
    @Description("Настройка тестового окружения: инициализация драйвера, переход на главную страницу, " +
            "создание Page Objects, настройка REST Assured")
    public void setUp() {
        String browser = System.getProperty("browser", Config.BROWSER_CHROME);
        driver = WebDriverFactory.createDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(Config.DEFAULT_TIMEOUT_SECONDS));

        driver.get(Config.MAIN_PAGE);
        mainPage = new MainPage(driver);
        authPage = new AuthPage(driver);

        RestAssured.baseURI = Config.API_URL;
    }

    @After
    @Description("Очистка тестового окружения: закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Создать пользователя через API")
    @Description("Создание тестового пользователя через REST API для использования в UI-тестах")
    protected User createUserThroughApi(String email, String password, String name) {
        User user = new User(email, password, name);

        Response response = given()
                .header(Config.CONTENT_TYPE_HEADER, Config.CONTENT_TYPE_JSON)
                .body(gson.toJson(user))
                .when()
                .post(Config.REGISTER_ENDPOINT);

        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            return user;
        }
        return null;
    }

    @Step("Удалить пользователя через API")
    @Description("Удаление тестового пользователя через REST API для очистки тестовых данных")
    protected void deleteUserThroughApi(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            given()
                    .header(Config.AUTHORIZATION_HEADER, accessToken)
                    .when()
                    .delete(Config.USER_ENDPOINT)
                    .then()
                    .statusCode(202);
        }
    }

    @Step("Получить access token пользователя")
    @Description("Получение access token для авторизованных API запросов")
    protected String getAccessToken(String email, String password) {
        String loginData = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        Response response = given()
                .header(Config.CONTENT_TYPE_HEADER, Config.CONTENT_TYPE_JSON)
                .body(loginData)
                .when()
                .post(Config.LOGIN_ENDPOINT);

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }

    @Step("Создать тестового пользователя")
    @Description("Создание уникального тестового пользователя с timestamp для избежания коллизий")
    protected User createUniqueTestUser() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "testuser" + timestamp + "@example.com";
        String password = "Password" + timestamp;
        String name = "TestUser" + timestamp;

        return createUserThroughApi(email, password, name);
    }
}