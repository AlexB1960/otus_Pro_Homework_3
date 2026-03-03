package pet;

import common.AbsMethodsDTO;
import common.Specifications;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Класс содержит тесты API-метода POST /pet/findByStatus
 * Add a new pet to the store
 */
public class PetPostTest extends AbsMethodsDTO {
  private final String baseURL = System.getProperty("baseURL");
  private final String pathURL = "/v2";
  private final String petURL = "/pet";
  private Specifications spec = new Specifications();

  /*Позитивный тест создания одного pet со значениями во всех полях.
  Проверка получения кода статуса 200 через спецификацию,
  проверка части полученных значений через Matchers.equalTo в составе body(),
  проверка второй части полученных значений через soft assert
  и валидация схемы postpetschema.json
  */
  @Test
  public void createPet200() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(0L)
        .category(setCategory("dog"))
        .name("Такса")
        .photoUrls(setPhotoUrl("https://images/first.jpg", "https://images/second.jpg"))
        .tags(setTags("Tag N1", "Tag N2", "Tag N3", "Tag N4"))
        .status("available")
        .build();

    PetResponseDTO petResponseDTO =
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
          .body(matchesJsonSchemaInClasspath("postpetschema.json"))
          .body("category.name", Matchers.equalTo("dog"))
          .body("name", Matchers.equalTo("Такса"))
          .body("photoUrls[0]", Matchers.equalTo("https://images/first.jpg"))
          .body("tags[1].name", Matchers.equalTo("Tag N2"))
          .extract().body().as(PetResponseDTO.class);

    Assertions.assertAll(
        () -> Assertions.assertEquals(petRequestDTO.getStatus(), petResponseDTO.getStatus(), "Incorrect status"),
        () -> Assertions.assertEquals(petRequestDTO.getCategory(), petResponseDTO.getCategory(), "Incorrect category"),
        () -> Assertions.assertEquals(petRequestDTO.getTags(), petResponseDTO.getTags(), "Incorrect tags"),
        () -> Assertions.assertEquals(petRequestDTO.getPhotoUrls(), petResponseDTO.getPhotoUrls(), "Incorrect photoUrls")
    );
  }

  /*Негативный тест создания pet через запрос get.
  Проверка получения кода статуса 405 через спецификацию.
  */
  @Test
  public void createPet405() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(405));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .body("{\n" +
            "  \"id\": 0\n" +
            "}")
        .when()
          .get(petURL) //get вместо post
        .then()
          .log().all();
  }

}
