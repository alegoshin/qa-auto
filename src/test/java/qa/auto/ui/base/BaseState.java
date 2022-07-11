package qa.auto.ui.base;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static com.codeborne.selenide.Selenide.open;

public class BaseState {

    @BeforeMethod
    public void setup(Method method) {
        Configuration.baseUrl = parseBaseUrl(method);
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
        open("/");
    }

    @AfterMethod
    public void teardown() {

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