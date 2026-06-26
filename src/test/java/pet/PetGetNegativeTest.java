package pet;

import com.google.inject.Inject;
import extensions.PetExtensions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.PetStore;

/**
 * Класс содержит негативные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
@ExtendWith(PetExtensions.class)
@Epic("Тесты сайта petstore.swagger.io")
@Story("Негативные тесты")
@DisplayName("Негативные тесты получения животных сайта petstore.swagger.io")
public class PetGetNegativeTest {
  @Inject
  private PetStore petStore;

  /*Негативный тест получения списка всех pets с несуществующим статусом WrongStatus.
  Проверка получения кода статуса 400 через спецификацию.
  Тест провален - запрос выполняется с невалидным значением WrongStatus
  и возвращает код статуса 200 вместо кода 400.
  */
  @Test
  @DisplayName("Получение списка животных по несуществующему статусу WrongStatus")
  @Feature("Статус кода 400")
  public void getPets400() {
    petStore.spec.installSpecification(petStore.spec.requestSpec(petStore.baseURL, petStore.pathURL),
        petStore.spec.responseSpec(400));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", "WrongStatus")
        .when()
          .get(petStore.petsURL)
        .then()
          .statusCode(HttpStatus.SC_BAD_REQUEST); //SC_BAD_REQUEST
  }

}
