package qa.auto.ui.page;

import io.qameta.allure.Step;
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
    @Step("Filling search input via text: \"{term}\" and press 'Enter'")
    public YandexResultsPage search(String term) {
        $(searchInput).setValue(term).pressEnter();
        return new YandexResultsPage();
    }

}