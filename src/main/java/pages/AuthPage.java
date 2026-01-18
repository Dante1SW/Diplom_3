package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


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
    public void setLoginEmail(String email) {
        loginEmailField.clear();
        loginEmailField.sendKeys(email);
    }

    public void setLoginPassword(String password) {
        loginPasswordField.clear();
        loginPasswordField.sendKeys(password);
    }

    public void clickLoginSubmitButton() {
        loginButton.click();
    }

    public void clickRegisterLink() {
        registerLink.click();
    }



    public void login(String email, String password) {
        setLoginEmail(email);
        setLoginPassword(password);
        clickLoginSubmitButton();
    }


    public void setRegistrationName(String name) {
        regNameField.clear();
        regNameField.sendKeys(name);
    }

    public void setRegistrationEmail(String email) {
        regEmailField.clear();
        regEmailField.sendKeys(email);
    }

    public void setRegistrationPassword(String password) {
        regPasswordField.clear();
        regPasswordField.sendKeys(password);
    }

    public void clickRegistrationSubmitButton() {
        registerButton.click();
    }

    public void clickRegistrationLoginLink() {
        regLoginLink.click();
    }

    public void register(String name, String email, String password) {
        setRegistrationName(name);
        setRegistrationEmail(email);
        setRegistrationPassword(password);
        clickRegistrationSubmitButton();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }

    public void waitForErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
    }


    public void clickRecoverButton() {
        recoverButton.click();
    }

    public void clickRecoveryLoginLink() {
        recoveryLoginLink.click();
    }


    public void goToRegistrationPage() {
        clickRegisterLink();
    }


    public void goToLoginPage() {
        // Проверяем текущую страницу и кликаем соответствующую ссылку
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("/register")) {
            clickRegistrationLoginLink();
        } else if (currentUrl.contains("/forgot-password")) {
            clickRecoveryLoginLink();
        }
    }
}