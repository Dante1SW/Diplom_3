package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browser);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(chromeOptions);
    }

    private static WebDriver createYandexDriver() {
        String yandexPath = Config.YANDEX_BROWSER_PATH;

        try {
            WebDriverManager.chromedriver().browserVersion("142").setup();
        } catch (Exception e) {
            WebDriverManager.chromedriver().setup();
        }

        ChromeOptions yandexOptions = new ChromeOptions();
        yandexOptions.setBinary(yandexPath);
        yandexOptions.addArguments("--incognito");
        yandexOptions.addArguments("--start-maximized");
        yandexOptions.addArguments("--no-sandbox");
        yandexOptions.addArguments("--disable-dev-shm-usage");
        yandexOptions.addArguments("--remote-allow-origins=*");

        try {
            return new ChromeDriver(yandexOptions);
        } catch (Exception e) {
            return createChromeDriver();
        }
    }
}