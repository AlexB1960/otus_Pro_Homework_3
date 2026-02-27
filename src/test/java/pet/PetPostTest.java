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

  //Позитивный тест создания pet, возвращает код статуса 200
  @Test
  public void createPet200Test() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200, null, null));

    /*ArrayList<String> photoUrls = new ArrayList<>();
    photoUrls.add("https://images/first.jpg");
    photoUrls.add("https://images/second.jpg");*/

    /*Tag tag = Tag.builder()
        .id(0)
        .name("Tag N1")
        .build();
    ArrayList<Tag> tags = new ArrayList<>();
    tags.add(tag);
    tag = Tag.builder()
        .id(0)
        .name("Tag N2")
        .build();
    tags.add(tag);*/

    Category category = Category.builder()
        .id(0)
        .name("dog")
        .build();

    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(0)
        .category(category)
        .name("Такса")
        .photoUrls(getPhotoUrl("https://images/first.jpg", "https://images/second.jpg"))
        .tags(getTags("Tag N1", "Tag N2"))
        //.tags(tags)
        .status("available")
        .build();


    RestAssured.given()
          //.baseUri(baseURL)
          //.basePath(pathURL)
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .body(petRequestDTO)
          /*.body("{\n" +
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
              "}")*/
        .when()
          .post(petURL)
        .then()
          .log().all()
          .assertThat().body(matchesJsonSchemaInClasspath("petschema.json"));

  }

  ArrayList<String> getPhotoUrl(String... urlArgs) {
    ArrayList<String> photoUrls = new ArrayList<>();
    for (String photoUrl : urlArgs) {
      photoUrls.add(photoUrl);
    }
    /*photoUrls.add("https://images/first.jpg");
    photoUrls.add("https://images/second.jpg");*/
    return photoUrls;
  }

  ArrayList<Tag> getTags(String... tagArgs) {
    ArrayList<Tag> tags = new ArrayList<>();
    for (String tagName : tagArgs) {
      Tag tag = Tag.builder()
          .id(0)
          .name(tagName)
          .build();
      tags.add(tag);
    }
    return tags;
  }

}
