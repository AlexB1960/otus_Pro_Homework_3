package pet;

import com.google.inject.Inject;
import extensions.PetExtensions;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.Pets;

/**
 * Класс содержит негативные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
@ExtendWith(PetExtensions.class)
public class PetGetNegativeTest {//extends AbsMethodsPet
  @Inject
  private Pets pet;

  /*Негативный тест получения списка всех pets с несуществующим статусом WrongStatus.
  Проверка получения кода статуса 400 через спецификацию.
  Тест провален - запрос выполняется с невалидным значением WrongStatus
  и возвращает код статуса 200 вместо кода 400.
  */
  @Test
  public void getPets400() {
    pet.spec.installSpecification(pet.spec.requestSpec(pet.baseURL, pet.pathURL),
        pet.spec.responseSpec(400));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", "WrongStatus")
        .when()
          .get(pet.petsURL)
        .then()
          .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}
