package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Тест проверяет успешную регистрацию нового пользователя с валидными данными. " +
            "После регистрации проверяется редирект на страницу входа. " +
            "Созданный пользователь удаляется через API для очистки тестовых данных")
    public void successfulRegistrationTest() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "testuser" + timestamp + "@example.com";
        String password = "Password" + timestamp;
        String name = "TestUser" + timestamp;

        mainPage.goToLoginPage();
        authPage.goToRegistrationPage();
        authPage.register(name, email, password);

        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getCurrentUrl().contains("/login"));

        deleteUserThroughApi(getAccessToken(email, password));
    }

    @Test
    @DisplayName("Ошибка при некорректном пароле")
    @Description("Тест проверяет отображение ошибки при попытке регистрации с паролем менее 6 символов. " +
            "Проверяется отображение сообщения об ошибке и его текст")
    public void registrationWithInvalidPasswordTest() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String email = "testuser" + timestamp + "@example.com";
        String password = "12345";
        String name = "TestUser" + timestamp;

        mainPage.goToLoginPage();
        authPage.goToRegistrationPage();
        authPage.register(name, email, password);

        authPage.waitForErrorMessage();
        assertTrue(authPage.isErrorMessageDisplayed());

        String errorText = authPage.getErrorMessageText();
        assertTrue(errorText.contains("Некорректный пароль") || errorText.contains("6"));
    }
}