package pet;

import common.AbsMethodsPetsDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Класс содержит негативные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
public class PetGetNegativeTest extends AbsMethodsPetsDTO {

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
          .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}
