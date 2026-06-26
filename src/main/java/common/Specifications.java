package common;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {

  public RequestSpecification requestSpec(String url, String path) {
    return new RequestSpecBuilder()
        .setBaseUri(url)
        .setBasePath(path)
        .setContentType(ContentType.JSON)
        .addFilter(new AllureRestAssured())
        .build();
  }

  public ResponseSpecification responseSpec(Integer code) {
    return new ResponseSpecBuilder()
        .expectStatusCode(code)
        .build();
  }

  public void installSpecification(RequestSpecification request, ResponseSpecification response) {
    RestAssured.requestSpecification = request;
    RestAssured.responseSpecification = response;
  }

}
