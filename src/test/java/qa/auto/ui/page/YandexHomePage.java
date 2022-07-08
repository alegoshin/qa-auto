package qa.auto.ui.page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class YandexHomePage {

    // locators
    private final By searchInput = By.id("text");

    // methods

    /**
     * Fill search input and press 'Enter'
     *
     * @param term value of search term
     * @return yandex results page
     */
    public YandexResultsPage search(String term) {
        $(searchInput).setValue(term).pressEnter();
        return new YandexResultsPage();
    }

}