package pet;

import common.AbsMethodsDTO;
import common.Specifications;
import dto.PetResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetGetTest extends AbsMethodsDTO {
  private final String baseURL = System.getProperty("baseURL");
  private final String pathURL = "/v2";
  private final String petsURL = "/pet/findByStatus";
  private Specifications spec = new Specifications();

  //Позитивный тест получения всех pets со статусом available
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
          //можно . можно $
          .extract().body().jsonPath().getList(".", PetResponseDTO.class);

    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

  //Негативный тест получения всех pets с несуществующим статусом WrongStatus
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
