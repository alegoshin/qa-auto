package qa.auto.ui.test;

import org.junit.Assert;
import org.junit.Test;
import qa.auto.ui.base.BaseYandexState;
import qa.auto.ui.page.YandexHomePage;

import java.util.List;

public class YandexSearchTest extends BaseYandexState {

    @Test
    public void testYandexSimpleSearch() {

        String searchQuery = "Тест";

        List<String> results = new YandexHomePage()
                .search(searchQuery)
                .getResultsWithoutAds();

        Assert.assertTrue(
                "Not all results match the search term: \nResults list: " + results.toString()
                        + "\n search term: " + searchQuery,
                results.stream().allMatch(r -> r.toLowerCase().contains(searchQuery.toLowerCase())));
    }

}