package qa.auto.api.base;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import util.PropertiesManager;

import static io.restassured.RestAssured.given;

public class ApiBaseState {

    protected static final String URL = PropertiesManager.getProperty("api.properties").getProperty("baseUrl");

    @Step("GET to {resourceUrl}")
    protected static Response get(String resourceUrl, int statusCode) {
        return given(Specifications.baseSpecification(URL))
                .get(resourceUrl)
                .then().statusCode(statusCode)
                .extract().response();
    }

    @Step("POST to {resourceUrl} with body {body}")
    protected static Response post(String resourceUrl, Object body, int statusCode) {
        return given(Specifications.baseSpecification(URL))
                .body(body)
                .post(resourceUrl)
                .then().statusCode(statusCode)
                .extract().response();
    }

    @Step("PUT to {resourceUrl} with body {body}")
    protected static Response put(String resourceUrl, Object body, int statusCode) {
        return given(Specifications.baseSpecification(URL))
                .body(body)
                .put(resourceUrl)
                .then().statusCode(statusCode)
                .extract().response();
    }

    @Step("PUT to {resourceUrl} with body {body}")
    protected static Response delete(String resourceUrl, Object body, int statusCode) {
        return given(Specifications.baseSpecification(URL))
                .body(body == null ? new Object() : body)
                .delete(resourceUrl)
                .then().statusCode(statusCode)
                .extract().response();
    }

}