package pet;

import common.Specifications;
import dto.Category;
import dto.PetRequestDTO;
import dto.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetPostTest {
  private final String baseURL = System.getProperty("baseURL");
  private final String pathURL = "/v2";
  private final String petURL = "/pet";
  private Specifications spec = new Specifications();

  //Создание нужных тестовых данных - токенов и ID пользователей
  @BeforeEach
  public void setTestData() {

  }

  //Удаление всех динамически созданных тестовых данных и закрытие SqlConnector
  @AfterEach
  public void deleteTestData() {

  }

  @Test
  public void createPet200Test() {
    spec.installSpecification(spec.requestSpec(baseURL),
        spec.responseSpec(200, null, null));
    Tag tags = Tag.builder()
        .id(0)
        .name("firstTag")
        .build();

    Category category = Category.builder()
        .id(0)
        .name("dog")
        .build();

    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(0)
        .category(category)
        .name("Такса")
        //.photoUrls()
        //.tags(tags)
        //.status()
        .build();


    RestAssured.given()
          .baseUri(baseURL)
          .basePath(pathURL)
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          //.body(petRequestDTO)
          .body("{\n" +
              "  \"id\": 0,\n" +
              "  \"category\": {\n" +
              "    \"id\": 0,\n" +
              "    \"name\": \"Tax\"\n" +
              "  },\n" +
              "  \"name\": \"dog\",\n" +
              "  \"photoUrls\": [\n" +
              "    \"dog.ru\"\n" +
              "  ],\n" +
              "  \"tags\": [\n" +
              "    {\n" +
              "      \"id\": 0,\n" +
              "      \"name\": \"string\"\n" +
              "    }\n" +
              "  ],\n" +
              "  \"status\": \"available\"\n" +
              "}")
        .when()
          .post(petURL)
        .then()
          .log().all()
          .assertThat().body(matchesJsonSchemaInClasspath("petschema.json"));

  }

}
