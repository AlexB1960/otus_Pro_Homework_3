package pet;

import common.AbsMethodsPetsDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

/**
 * Класс содержит негативные тесты API-метода POST /pet/findByStatus
 * Add a new pet to the store
 */
public class PetPostNegativeTest extends AbsMethodsPetsDTO {

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
          .body("{\n"
              + "  \"id\": 0\n"
              + "}")
        .when()
          .get(petURL) //get вместо post
        .then()
          .log().all();
  }

}
