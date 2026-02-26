package common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
//import static org.hamcrest.Matchers.lessThan;

public class Specifications {

    public RequestSpecification requestSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    public ResponseSpecification responseSpec(Integer code, String type, String message) {
        return new ResponseSpecBuilder()
                .expectBody("message", Matchers.equalTo(message))
                .expectBody("type", Matchers.equalTo(type))
                //.expectResponseTime(lessThan(5000L))
                .expectStatusCode(code)
                .build();
    }

    public void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

}
