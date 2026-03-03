package pet;

import common.AbsMethodsDTO;
import common.Specifications;
import dto.PetResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Класс содержит тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
public class PetGetTest extends AbsMethodsDTO {
  private final String baseURL = System.getProperty("baseURL");
  private final String pathURL = "/v2";
  private final String petsURL = "/pet/findByStatus";
  private Specifications spec = new Specifications();

  /*Позитивный тест получения списка всех pets со статусом available.
  Проверка получения кода статуса 200 через спецификацию,
  проверка значения available в поле status во всех полученных pets
  и валидация схемы getpetschema.json
  */
  @Test
  public void getPets200() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    List<PetResponseDTO> pets =
    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", "available")
        .when()
          .get(petsURL)
        .then()
          .log().ifValidationFails()
          .assertThat()
          .body(matchesJsonSchemaInClasspath("getpetschema.json"))
          .extract().body().jsonPath().getList(".", PetResponseDTO.class);

    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

  /*Негативный тест получения списка всех pets с несуществующим статусом WrongStatus.
  Проверка получения кода статуса 400 через спецификацию.
  Тест провален - запрос выполняется с невалидным значением WrongStatus
  и возвращает код статуса 200 вместо кода 400.
  */
  @Test
  public void getPets400() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(400));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", "WrongStatus")
        .when()
          .get(petsURL)
        .then()
          .log().all();
  }

}
