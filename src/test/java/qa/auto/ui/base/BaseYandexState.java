package qa.auto.ui.base;

import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.open;

public class BaseYandexState {

    @Before
    public void setup() {
        Configuration.baseUrl = "https://yandex.ru";
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
        open("/");
    }

    @After
    public void teardown() {

    }

}