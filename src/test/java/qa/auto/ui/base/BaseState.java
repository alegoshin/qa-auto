package qa.auto.ui.base;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseState {

    @BeforeMethod
    @Step("Browser configuration")
    public void setup(Method method) {
        Configuration.baseUrl = parseBaseUrl(method);
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
        open("/");
    }

    @AfterMethod
    @Step("Browser closing")
    public void teardown() {
        closeWebDriver();
    }

    private String parseBaseUrl(Method method) {
        String url;
        if (method.isAnnotationPresent(BaseUrl.class)) {
            url = method.getAnnotation(BaseUrl.class).url();
        } else if (method.getDeclaringClass().isAnnotationPresent(BaseUrl.class)) {
            url = method.getDeclaringClass().getAnnotation(BaseUrl.class).url();
        } else {
            url = "https://yandex.ru";
        }
        return url;
    }

}