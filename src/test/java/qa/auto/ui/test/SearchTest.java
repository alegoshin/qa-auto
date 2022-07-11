package qa.auto.ui.test;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import qa.auto.ui.base.BaseState;
import qa.auto.ui.page.YandexHomePage;
import qa.auto.ui.page.YandexResultsPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@Epic(value = "Search")
@Feature(value = "Simple search")
public class SearchTest extends BaseState {

    private final String searchQuery = "Тест";

    @Test(description = "Simple search and results checking")
    public void testYandexSimpleSearch() {

        List<String> results = new YandexHomePage()
                .search(searchQuery)
                .results().exclude(Condition.text("Реклама")).texts();

        Assert.assertTrue(
                results.stream().allMatch(r -> r.toLowerCase().contains(searchQuery.toLowerCase())),
                "Not all results match the search term: \nResults list: " + results.toString()
                        + "\n search term: " + searchQuery);
    }

    @Test(description = "Simple search and results opening")
    public void testYandexOpenResult() {

        YandexResultsPage yandexResultsPage = new YandexHomePage().search(searchQuery);

        int resultsCount = yandexResultsPage.results().size();
        for (int i = 0; i < resultsCount; i++) {
            yandexResultsPage.openResult(i);

            // check result page have mentions of search query
            Assert.assertTrue(
                    webdriver().driver().source().contains(searchQuery),
                    "Page from results don't have the search query");

            closeWindow();
            switchTo().window(0);
        }
    }

}