package qa.auto.ui.page;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class YandexResultsPage {

    // locators
    private final By resultsList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]");
    private final By resultsHeadersList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]//h2");

    // methods

    /**
     * Return all text from results cards
     *
     * @return list with results
     */
    public List<String> getResults() {
        return $$(resultsList).texts();
    }

    /**
     * Return all text from results cards exclude ads items
     *
     * @return list with results
     */
    public List<String> getResultsWithoutAds() {
        return $$(resultsList).exclude(Condition.text("Реклама")).texts();
    }

    public int getResultsCount() {
        return $$(resultsList).size();
    }

    public void clickResult(int index) {
        int windowsCount = webdriver().object().getWindowHandles().size();
        $$(resultsHeadersList).get(index).click();
        switchTo().window(windowsCount);
    }

}