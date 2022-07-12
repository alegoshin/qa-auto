package qa.auto.ui.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class YandexResultsPage {

    // locators
    private final By resultsList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]");
    private final By resultsHeadersList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]//h2");
    private final By paginationItems = By.xpath("//div[@class='pager__items']/*[contains(@class, 'pager__item')]");

    // methods
    public ElementsCollection results() {
        return $$(resultsList);
    }

    public ElementsCollection resultsWithoutAds() {
        return results().exclude(Condition.text("Реклама"));
    }

    public ElementsCollection resultsHeaders() {
        return $$(resultsHeadersList);
    }

    public ElementsCollection paginationItems() {
        return $$(paginationItems);
    }

    @Step("Click on result by index {index}")
    public void openResult(int index) {
        int windowsCount = webdriver().object().getWindowHandles().size();
        resultsHeaders().get(index).click();
        switchTo().window(windowsCount);
    }

    private List<Integer> getAvailablePageNumbers() {
        return paginationItems().texts().stream()
                .filter(p -> p.matches("\\d+"))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    @Step("Select page #{pageNumber}")
    public YandexResultsPage selectPage(int pageNumber) {

        List<Integer> availableNumbers = getAvailablePageNumbers();

        for (int i = 0; i < 10 && !availableNumbers.contains(pageNumber); i++) {
            if (Collections.max(availableNumbers) < pageNumber) {
                paginationItems().get(paginationItems().size() - 2).click();
            } else {
                paginationItems().get(1).click();
            }
            availableNumbers = getAvailablePageNumbers();
        }

        SelenideElement queryPage = paginationItems().shouldHave(CollectionCondition.itemWithText(String.valueOf(pageNumber)))
                .find(Condition.text(String.valueOf(pageNumber)));
        if (!queryPage.getTagName().equals("span")) {
            queryPage.click();
        }

        return this;
    }

}