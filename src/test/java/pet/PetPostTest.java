package pet;

import common.AbsMethodsDTO;
import common.Specifications;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

public class PetPostTest extends AbsMethodsDTO {
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

    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(0)
        .category(setCategory("dog"))
        .name("Такса")
        .photoUrls(setPhotoUrl("https://images/first.jpg", "https://images/second.jpg"))
        .tags(setTags("Tag N1", "Tag N2", "Tag N3", "Tag N4"))
        .status("available")
        .build();

    //PetResponseDTO petResponseDTO =
    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .body(petRequestDTO)
        .when()
          .post(petURL)
        .then()
          .log().ifValidationFails()
          .assertThat()
          .body(matchesJsonSchemaInClasspath("petschema.json"))
          .body("category.name", Matchers.equalTo("dog"))
          .body("name", Matchers.equalTo("Такса"))
          .body("photoUrls[0]", Matchers.equalTo("https://images/first.jpg"))
          .body("tags[1].name", Matchers.equalTo("Tag N2"));
          //.extract().body().as(PetResponseDTO.class);

  }

  @Test
  public void createPet405Test() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(405, null, null));

    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(-1)
        .category(setCategory(""))
        .name("")
        .photoUrls(setPhotoUrl(""))
        .tags(setTags(""))
        .status("")
        .build();

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .auth().oauth2("")
        .body(petRequestDTO)
        .when()
        .post(petURL)
        .then()
        .log().ifValidationFails()
        .assertThat()
        .body(matchesJsonSchemaInClasspath("petschema.json"));
        /*.body("category.name", Matchers.equalTo("dog"))
        .body("name", Matchers.equalTo("Такса"))
        .body("photoUrls[0]", Matchers.equalTo("https://images/first.jpg"))
        .body("tags[1].name", Matchers.equalTo("Tag N2"));*/

  }

}
