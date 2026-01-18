package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import models.User;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Config;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Тест проверяет авторизацию пользователя через кнопку 'Войти в аккаунт' на главной странице. " +
            "Создается тестовый пользователь через API, выполняется вход и проверяется редирект на главную страницу")
    public void loginViaMainPageButtonTest() {
        User user = createUniqueTestUser();
        assertTrue(user != null);

        mainPage.goToLoginPage();
        authPage.login(user.getEmail(), user.getPassword());

        wait.until(ExpectedConditions.urlToBe(Config.MAIN_PAGE));
        assertTrue(driver.getCurrentUrl().equals(Config.MAIN_PAGE));

        deleteUserThroughApi(getAccessToken(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Тест проверяет авторизацию пользователя через кнопку 'Личный кабинет' в шапке сайта. " +
            "Создается тестовый пользователь через API, выполняется вход и проверяется редирект на главную страницу")
    public void loginViaPersonalAccountButtonTest() {
        User user = createUniqueTestUser();
        assertTrue(user != null);

        mainPage.goToLoginPageViaPersonalAccount();
        authPage.login(user.getEmail(), user.getPassword());

        wait.until(ExpectedConditions.urlToBe(Config.MAIN_PAGE));
        assertTrue(driver.getCurrentUrl().equals(Config.MAIN_PAGE));

        deleteUserThroughApi(getAccessToken(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Тест проверяет авторизацию пользователя через ссылку 'Войти' на странице регистрации. " +
            "Создается тестовый пользователь через API, выполняется переход на страницу регистрации, " +
            "переход на страницу входа и авторизация")
    public void loginViaRegistrationFormLinkTest() {
        User user = createUniqueTestUser();
        assertTrue(user != null);

        driver.get(Config.REGISTER_PAGE);
        authPage.goToLoginPage();
        authPage.login(user.getEmail(), user.getPassword());

        wait.until(ExpectedConditions.urlToBe(Config.MAIN_PAGE));
        assertTrue(driver.getCurrentUrl().equals(Config.MAIN_PAGE));

        deleteUserThroughApi(getAccessToken(user.getEmail(), user.getPassword()));
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Тест проверяет авторизацию пользователя через ссылку 'Войти' на странице восстановления пароля. " +
            "Создается тестовый пользователь через API, выполняется переход на страницу восстановления пароля, " +
            "переход на страницу входа и авторизация")
    public void loginViaForgotPasswordFormLinkTest() {
        User user = createUniqueTestUser();
        assertTrue(user != null);

        driver.get(Config.FORGOT_PASSWORD_PAGE);
        authPage.goToLoginPage();
        authPage.login(user.getEmail(), user.getPassword());

        wait.until(ExpectedConditions.urlToBe(Config.MAIN_PAGE));
        assertTrue(driver.getCurrentUrl().equals(Config.MAIN_PAGE));

        deleteUserThroughApi(getAccessToken(user.getEmail(), user.getPassword()));
    }
}