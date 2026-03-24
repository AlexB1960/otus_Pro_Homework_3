package pets;

import common.Specifications;
import dto.Category;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import dto.Tag;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pets {
  public static String baseURL = System.getProperty("baseURL");
  public static String pathURL = "/v2";
  public final String petURL = "/pet";
  public static String petIdURL = "/pet/{petId}";
  public final String petsURL = "/pet/findByStatus";
  public Specifications spec = new Specifications();

  public ArrayList<String> setPhotoUrl(String... urlArgs) {
    ArrayList<String> photoUrls = new ArrayList<>();
    /*for (String photoUrl : urlArgs) {
      photoUrls.add(photoUrl);
    }*/
    Collections.addAll(photoUrls, urlArgs);
    return photoUrls;
  }

  public ArrayList<Tag> setTags(String... tagArgs) {
    ArrayList<Tag> tags = new ArrayList<>();
    Long i = 0L;
    for (String tagName : tagArgs) {
      Tag tag = Tag.builder()
          .id(i)
          .name(tagName)
          .build();
      tags.add(tag);
      i++;
    }
    return tags;
  }

  public Category setCategory(String nameCategory) {
    Category category = Category.builder()
        .id(0L)
        .name(nameCategory)
        .build();
    return category;
  }

  public List<PetResponseDTO> getPetsByStatus(String status) {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    return RestAssured
        .given()
        .contentType(ContentType.JSON)
        .auth().oauth2("")
        .queryParam("status", status)
        .when()
        .get(petsURL)
        .then()
        .log().ifValidationFails()
        .assertThat()
        .body(JsonSchemaValidator
            .matchesJsonSchemaInClasspath("getpetschema.json"))
        .extract().body().jsonPath().getList(".", PetResponseDTO.class);
  }

  public PetResponseDTO getPetById(Long petId) {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    return RestAssured
        .given()
        .contentType(ContentType.JSON)
        .auth().oauth2("")
        .pathParam("petId", petId)
        .when()
        .get(petIdURL)
        .then()
        .log().ifValidationFails()
        .assertThat()
        .body(JsonSchemaValidator
            .matchesJsonSchemaInClasspath("postpetschema.json"))
        .extract().body().jsonPath().getObject(".", PetResponseDTO.class);
  }

  public PetResponseDTO addNewPet(PetRequestDTO petRequestDTO) {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    return RestAssured
        .given()
        .contentType(ContentType.JSON)
        .auth().oauth2("")
        .body(petRequestDTO)
        .when()
        .post(petURL)
        .then()
        .log().ifValidationFails()
        .assertThat()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("postpetschema.json"))
        .extract().body().as(PetResponseDTO.class);
  }

  public void assertCreatedPet(PetRequestDTO petRequestDTO, PetResponseDTO createdPet) {
    Assertions.assertAll(
        () -> Assertions.assertEquals(petRequestDTO.getCategory(), createdPet
            .getCategory(), "Incorrect category"),
        () -> Assertions.assertEquals(petRequestDTO.getName(), createdPet
            .getName(), "Incorrect name"),
        () -> Assertions.assertEquals(petRequestDTO.getPhotoUrls(), createdPet
            .getPhotoUrls(), "Incorrect photoUrls"),
        () -> Assertions.assertEquals(petRequestDTO.getTags(), createdPet
            .getTags(), "Incorrect tags"),
        () -> Assertions.assertEquals(petRequestDTO.getStatus(), createdPet
            .getStatus(), "Incorrect status")
    );
  }

  public void deletePetById(Long petId) {
    spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));

    RestAssured
        .given()
        .contentType(ContentType.JSON)
        .auth().oauth2("")
        .pathParam("petId", petId)
        .when()
        .delete(petIdURL)
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

}
