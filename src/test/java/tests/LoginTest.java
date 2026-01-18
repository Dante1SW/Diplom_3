package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.User;
import utils.Config;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseTest {
    private User testUser;
    private String accessToken;

    @Before
    @Step("Создание тестового пользователя")
    public void createTestUser() {
        testUser = createUniqueTestUser();
        assertTrue("Не удалось создать тестового пользователя", testUser != null);
    }

    @After
    @Step("Удаление тестового пользователя")
    public void deleteTestUser() {
        if (testUser != null) {
            String token = getAccessToken(testUser.getEmail(), testUser.getPassword());
            if (token != null) {
                deleteUserThroughApi(token);
            }
        }
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Тест проверяет авторизацию пользователя через кнопку 'Войти в аккаунт' на главной странице. " +
            "Создается тестовый пользователь через API, выполняется вход и проверяется редирект на главную страницу")
    public void loginViaMainPageButtonTest() {
        mainPage.goToLoginPage();
        authPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("Не удалось выполнить вход через главную страницу",
                authPage.isOpened());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Тест проверяет авторизацию пользователя через кнопку 'Личный кабинет' в шапке сайта. " +
            "Создается тестовый пользователь через API, выполняется вход и проверяется редирект на главную страницу")
    public void loginViaPersonalAccountButtonTest() {
        mainPage.goToLoginPageViaPersonalAccount();
        authPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("Не удалось выполнить вход через личный кабинет",
                authPage.isOpened());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Тест проверяет авторизацию пользователя через ссылку 'Войти' на странице регистрации. " +
            "Создается тестовый пользователь через API, выполняется переход на страницу регистрации, " +
            "переход на страницу входа и авторизация")
    public void loginViaRegistrationFormLinkTest() {
        driver.get(Config.REGISTER_PAGE);
        authPage.goToLoginPage();
        authPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("Не удалось выполнить вход со страницы регистрации",
                authPage.isOpened());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Тест проверяет авторизацию пользователя через ссылку 'Войти' на странице восстановления пароля. " +
            "Создается тестовый пользователь через API, выполняется переход на страницу восстановления пароля, " +
            "переход на страницу входа и авторизация")
    public void loginViaForgotPasswordFormLinkTest() {
        driver.get(Config.FORGOT_PASSWORD_PAGE);
        authPage.goToLoginPage();
        authPage.login(testUser.getEmail(), testUser.getPassword());

        assertTrue("Не удалось выполнить вход со страницы восстановления пароля",
                authPage.isOpened());
    }
}