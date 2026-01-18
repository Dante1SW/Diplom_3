package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {
    private String testEmail;
    private String testPassword;

    @After
    @Step("Удаление тестового пользователя после регистрации")
    public void deleteTestUserAfterRegistration() {
        if (testEmail != null && testPassword != null) {
            String token = getAccessToken(testEmail, testPassword);
            if (token != null) {
                deleteUserThroughApi(token);
            }
        }
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Тест проверяет успешную регистрацию нового пользователя с валидными данными. " +
            "После регистрации проверяется редирект на страницу входа. " +
            "Созданный пользователь удаляется через API для очистки тестовых данных")
    public void successfulRegistrationTest() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        testEmail = "testuser" + timestamp + "@example.com";
        testPassword = "Password" + timestamp;
        String name = "TestUser" + timestamp;

        mainPage.goToLoginPage();
        authPage.goToRegistrationPage();
        authPage.register(name, testEmail, testPassword);

        assertTrue("Не удалось зарегистрировать пользователя",
                authPage.isLoginPageOpened());
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