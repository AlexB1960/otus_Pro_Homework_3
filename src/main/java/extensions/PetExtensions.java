package extensions;

import com.google.inject.Guice;
import common.Specifications;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import modules.GuicePetsModule;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pets.Pets;
import java.util.ArrayList;
import java.util.List;

public class PetExtensions implements BeforeEachCallback, AfterEachCallback {
  /*@Inject
  private Pets pet;*/

  protected Specifications spec = new Specifications();
  public static List<Long> deletedList = null;

  @Override
  public void beforeEach(ExtensionContext context) {
    Guice.createInjector(new GuicePetsModule())
        .injectMembers(context.getTestInstance().get());
    if (deletedList == null) { //если удаление в прошлых тестах завершилось успешно
      deletedList = new ArrayList<>();
    }
  }

  @Override
  public void afterEach(ExtensionContext context) {
    if (deletedList != null) {
      for (Long el : deletedList) {
        deletePetById(el);
      }
      deletedList = null;
    }
  }

  protected void deletePetById(Long petId) {
    spec.installSpecification(spec.requestSpec(Pets.baseURL, Pets.pathURL),
        spec.responseSpec(200));

    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .pathParam("petId", petId)
        .when()
          .delete(Pets.petIdURL)
        .then()
          .statusCode(HttpStatus.SC_OK);
  }

}
