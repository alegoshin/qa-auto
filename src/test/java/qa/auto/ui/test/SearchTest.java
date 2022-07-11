package qa.auto.ui.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.auto.ui.base.BaseState;
import qa.auto.ui.page.YandexHomePage;
import qa.auto.ui.page.YandexResultsPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SearchTest extends BaseState {

    private final String searchQuery = "Тест";

    @Test
    public void testYandexSimpleSearch() {

        List<String> results = new YandexHomePage()
                .search(searchQuery)
                .getResultsWithoutAds();

        Assert.assertTrue(
                results.stream().allMatch(r -> r.toLowerCase().contains(searchQuery.toLowerCase())),
                "Not all results match the search term: \nResults list: " + results.toString()
                        + "\n search term: " + searchQuery);
    }

    @Test
    public void testYandexOpenResult() {

        YandexResultsPage yandexResultsPage = new YandexHomePage().search(searchQuery);

        int resultsCount = yandexResultsPage.getResultsCount();
        for (int i = 0; i < resultsCount; i++) {
            yandexResultsPage.clickResult(i);

            // check result page have mentions of search query
            Assert.assertTrue(
                    webdriver().driver().source().contains(searchQuery),
                    "Page from results don't have the search query");

            closeWindow();
            switchTo().window(0);
        }
    }

}