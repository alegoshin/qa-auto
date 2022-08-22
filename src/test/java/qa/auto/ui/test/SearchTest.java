package qa.auto.ui.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.auto.ui.base.UIBaseState;
import qa.auto.ui.page.YandexHomePage;
import qa.auto.ui.page.YandexResultsPage;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.codeborne.selenide.Selenide.*;

@Epic(value = "Search")
@Feature(value = "Simple search")
public class SearchTest extends UIBaseState {

    private final String searchQuery = "Тест";

    @BeforeMethod
    @Step("Navigate to results page with search query")
    private void navigateToResultsPage() {
        new YandexHomePage().search(searchQuery);
    }


    @Test(description = "Simple search and results checking")
    public void testYandexSimpleSearch() {

        List<String> results = new YandexResultsPage().resultsWithoutAds().texts();

        Assert.assertTrue(
                results.stream().allMatch(r -> r.toLowerCase().contains(searchQuery.toLowerCase())),
                "Not all results match the search term: " + searchQuery);
    }

    @Test(description = "Simple search and results opening")
    public void testYandexOpenResult() {

        YandexResultsPage yandexResultsPage = new YandexResultsPage();

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

    @Test(description = "Simple search and results checking with pagination")
    public void testYandexSimpleSearchWithPagination() {

        YandexResultsPage yandexResultsPage = new YandexResultsPage();

        HashSet<Integer> pages = new HashSet<>();
        for (int i = 0; i < 15 && pages.size() < 3; i++) {
            pages.add(ThreadLocalRandom.current().nextInt(5, 11));
        }

        for (int i : pages) {
            List<String> results = yandexResultsPage
                    .selectPage(i)
                    .resultsWithoutAds().texts();

            Assert.assertTrue(
                    results.stream().allMatch(r -> r.toLowerCase().contains(searchQuery.toLowerCase())),
                    "Not all results match the search term: " + searchQuery);
        }
    }

}