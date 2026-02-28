package pet;

import common.AbsMethodsDTO;
import common.Specifications;
import dto.PetResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.postgresql.gss.GSSOutputStream;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetGetTest extends AbsMethodsDTO {
  private final String baseURL = System.getProperty("baseURL");
  private final String pathURL = "/v2";
  private final String petsURL = "/pet/findByStatus";
  private Specifications spec = new Specifications();

  @Test
  public void getPets200() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200, null, null));

    List<PetResponseDTO> pets =
    RestAssured
        .given()
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", "available")
        .when()
          .get(petsURL)
        .then()
          .log().all()
          //.extract().response().jsonPath().getList(".", PetResponseDTO.class);
          .extract().body().jsonPath().getList(".", PetResponseDTO.class);
          //.extract().as(PetResponseDTO.class);
          //.extract().body().jsonPath().getList(".", PetResponseDTO.class);
    //JsonPath jsonPath = pets.jsonPath();
    //List<PetResponseDTO> petList = new ArrayList<>(jsonPath.get("$"));

    System.out.println("Размер списка = " + pets.size());
    System.out.println("Имя name = " + pets.getFirst().getName());
    /*System.out.println("Размер списка = " + pets.body().toString());
    System.out.println("Имя name = " + petList.getFirst().getName());*/
    //System.out.println(pets.getFirst().getName());
    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

  @Test
  public void getPets400() {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(400, null, null));

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
