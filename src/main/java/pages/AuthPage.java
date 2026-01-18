package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import utils.Config;

public class AuthPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public AuthPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Страница логина
    @FindBy(xpath = "//input[@name='name']")
    private WebElement loginEmailField;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement loginPasswordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement forgotPasswordLink;

    // Страница регистрации
    @FindBy(xpath = "//fieldset[1]//input")
    private WebElement regNameField;

    @FindBy(xpath = "//fieldset[2]//input")
    private WebElement regEmailField;

    @FindBy(xpath = "//fieldset[3]//input")
    private WebElement regPasswordField;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    @FindBy(xpath = "//p[contains(@class, 'input__error')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement regLoginLink;

    // Страница восстановления пароля
    @FindBy(xpath = "//input[@type='text']")
    private WebElement recoveryEmailField;

    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement recoverButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement recoveryLoginLink;

    // Методы страницы логина
    @Step("Ввод email для входа: {email}")
    public void setLoginEmail(String email) {
        loginEmailField.clear();
        loginEmailField.sendKeys(email);
    }

    @Step("Ввод пароля для входа")
    public void setLoginPassword(String password) {
        loginPasswordField.clear();
        loginPasswordField.sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginSubmitButton() {
        loginButton.click();
    }

    @Step("Перейти к странице регистрации")
    public void clickRegisterLink() {
        registerLink.click();
    }

    @Step("Выполнить вход с email: {email}")
    public void login(String email, String password) {
        setLoginEmail(email);
        setLoginPassword(password);
        clickLoginSubmitButton();
    }

    @Step("Ввод имени при регистрации: {name}")
    public void setRegistrationName(String name) {
        regNameField.clear();
        regNameField.sendKeys(name);
    }

    @Step("Ввод email при регистрации: {email}")
    public void setRegistrationEmail(String email) {
        regEmailField.clear();
        regEmailField.sendKeys(email);
    }

    @Step("Ввод пароля при регистрации")
    public void setRegistrationPassword(String password) {
        regPasswordField.clear();
        regPasswordField.sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegistrationSubmitButton() {
        registerButton.click();
    }

    @Step("Перейти к странице входа со страницы регистрации")
    public void clickRegistrationLoginLink() {
        regLoginLink.click();
    }

    @Step("Выполнить регистрацию с именем: {name} и email: {email}")
    public void register(String name, String email, String password) {
        setRegistrationName(name);
        setRegistrationEmail(email);
        setRegistrationPassword(password);
        clickRegistrationSubmitButton();
    }

    @Step("Проверить отображение сообщения об ошибке")
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessageText() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }

    @Step("Дождаться отображения сообщения об ошибке")
    public void waitForErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
    }

    @Step("Нажать кнопку 'Восстановить'")
    public void clickRecoverButton() {
        recoverButton.click();
    }

    @Step("Перейти к странице входа со страницы восстановления пароля")
    public void clickRecoveryLoginLink() {
        recoveryLoginLink.click();
    }

    @Step("Перейти на страницу регистрации")
    public void goToRegistrationPage() {
        clickRegisterLink();
    }

    @Step("Перейти на страницу входа")
    public void goToLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("/register")) {
            clickRegistrationLoginLink();
        } else if (currentUrl.contains("/forgot-password")) {
            clickRecoveryLoginLink();
        }
    }

    @Step("Проверить открытие страницы логина")
    public boolean isLoginPageOpened() {
        try {
            wait.until(ExpectedConditions.urlContains("/login"));
            return driver.getCurrentUrl().contains("/login");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить открытие главной страницы после входа")
    public boolean isOpened() {
        try {
            wait.until(ExpectedConditions.urlToBe(Config.MAIN_PAGE));
            return driver.getCurrentUrl().equals(Config.MAIN_PAGE);
        } catch (Exception e) {
            return false;
        }
    }
}