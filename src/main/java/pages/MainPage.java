package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriverWait wait;


    @FindBy(xpath = "//h1[contains(text(),'Соберите бургер')]")
    private WebElement mainHeader;

    @FindBy(xpath = "//p[normalize-space()='Личный Кабинет']")
    private WebElement personalProfileButton;

    @FindBy(xpath = "//button[contains(text(), 'Войти в аккаунт')]")
    private WebElement enterProfileButton;

    @FindBy(xpath = "//span[contains(text(), 'Булки')]")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[contains(text(), 'Соусы')]")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[contains(text(), 'Начинки')]")
    private WebElement fillingsTab;

    public MainPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickPersonalProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalProfileButton)).click();
    }

    public void clickEnterProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(enterProfileButton)).click();
    }

    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
        waitForTabToBeActive("Булки");
    }

    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
        waitForTabToBeActive("Соусы");
    }

    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
        waitForTabToBeActive("Начинки");
    }

    public boolean isOpened() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(mainHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTabActive(String tabName) {
        String xpath = "//div[contains(@class, 'tab_tab_type_current')]//span[text()='" + tabName + "']";
        try {
            WebElement activeTab = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(xpath))
            );
            return activeTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSectionHeaderVisible(String sectionName) {
        String xpath = "//h2[text()='" + sectionName + "']";
        try {
            WebElement header = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(xpath))
            );
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForTabToBeActive(String tabName) {
        wait.until(driver -> isTabActive(tabName));
    }


    public void goToLoginPage() {
        clickEnterProfileButton();
    }

    public void goToLoginPageViaPersonalAccount() {
        clickPersonalProfileButton();
    }
}