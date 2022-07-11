package qa.auto.ui.page;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class YandexResultsPage {

    // locators
    private final By resultsList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]");
    private final By resultsHeadersList = By.xpath("//li[@class='serp-item serp-item_card' and not(@data-fast-name='multiusurveys')]//h2");

    // methods
    public ElementsCollection results() {
        return $$(resultsList);
    }

    public ElementsCollection resultsHeaders() {
        return $$(resultsHeadersList);
    }

    @Step("Click on result by index {index}")
    public void openResult(int index) {
        int windowsCount = webdriver().object().getWindowHandles().size();
        resultsHeaders().get(index).click();
        switchTo().window(windowsCount);
    }

}