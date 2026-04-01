package pet;

import com.google.inject.Inject;
import extensions.PetExtensions;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.PetStore;

/**
 * Класс содержит негативные тесты API-метода POST /pet/findByStatus
 * Add a new pet to the store
 */
@ExtendWith(PetExtensions.class)
public class PetPostNegativeTest {
  @Inject
  private PetStore petStore;

  /*Негативный тест создания pet через запрос get.
  Проверка получения кода статуса 405 через спецификацию.
  */
  @Test
  public void createPet405() {
    petStore.spec.installSpecification(petStore.spec.requestSpec(petStore.baseURL, petStore.pathURL),
        petStore.spec.responseSpec(405));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .body("{\n"
              + "  \"id\": 0\n"
              + "}")
        .when()
          .get(petStore.petURL) //get вместо post
        .then()
          .log().all();
  }

}
