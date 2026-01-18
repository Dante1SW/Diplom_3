package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ConstructorNavigationTest extends BaseTest {

    @Test
    @DisplayName("Проверка переключения на вкладку 'Соусы'")
    @Description("Тест проверяет возможность переключения на вкладку 'Соусы' в конструкторе бургеров " +
            "и отображение соответствующего заголовка секции")
    public void shouldSwitchToSaucesTab() {
        assertTrue(mainPage.isOpened());
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isTabActive("Соусы"));
        assertTrue(mainPage.isSectionHeaderVisible("Соусы"));
    }

    @Test
    @DisplayName("Проверка переключения на вкладку 'Начинки'")
    @Description("Тест проверяет возможность переключения на вкладку 'Начинки' в конструкторе бургеров " +
            "и отображение соответствующего заголовка секции")
    public void shouldSwitchToFillingsTab() {
        assertTrue(mainPage.isOpened());
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isTabActive("Начинки"));
        assertTrue(mainPage.isSectionHeaderVisible("Начинки"));
    }

    @Test
    @DisplayName("Проверка переключения на вкладку 'Булки'")
    @Description("Тест проверяет возможность переключения на вкладку 'Булки' после навигации по другим вкладкам " +
            "и отображение соответствующего заголовка секции")
    public void shouldSwitchToBunsSection() {
        assertTrue(mainPage.isOpened());
        mainPage.clickSaucesTab();
        assertTrue(mainPage.isTabActive("Соусы"));
        mainPage.clickBunsTab();
        assertTrue(mainPage.isTabActive("Булки"));
        assertTrue(mainPage.isSectionHeaderVisible("Булки"));
    }

}