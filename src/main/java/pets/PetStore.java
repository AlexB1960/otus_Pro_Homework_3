package pets;

import common.Specifications;
import dto.Category;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import dto.Tag;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PetStore {
  public final String baseURL = System.getProperty("baseURL");
  public final String pathURL = "/v2";
  public final String petURL = "/pet";
  public final String petIdURL = "/pet/{petId}";
  public final String petsURL = "/pet/findByStatus";
  public Specifications spec = new Specifications();
  private Stack<Long> store = new Stack<>();

  public long getPet() {
    return store.pop();
  }

  public void savePet(long id) {
    store.push(id);
  }

  public boolean isEmpty() {
    return store.empty();
  }

  //@Step("Установить для животного ссылки на фото {urlArgs}")
  public ArrayList<String> setPhotoUrl(String... urlArgs) {
    ArrayList<String> photoUrls = new ArrayList<>();
    /*for (String photoUrl : urlArgs) {
      photoUrls.add(photoUrl);
    }*/
    Collections.addAll(photoUrls, urlArgs);
    return photoUrls;
  }

  //@Step("Установить для животного теги {tagArgs}")
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

  //@Step("Установить для животного категорию {nameCategory}")
  public Category setCategory(String nameCategory) {
    Category category = Category.builder()
        .id(0L)
        .name(nameCategory)
        .build();
    return category;
  }

  @Step("Получить животных со статусом {status}")
  public List<PetResponseDTO> getPetsByStatus(String status) {
    /*spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));*/

    return RestAssured
        .given(spec.requestSpec(baseURL, pathURL))
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .queryParam("status", status)
        .when()
          .get(petsURL)
        .then()
          .log().ifValidationFails()
          .spec(spec.responseSpec(200))
          .assertThat()
          .body(JsonSchemaValidator
              .matchesJsonSchemaInClasspath("getpetschema.json"))
          .extract().body().jsonPath().getList(".", PetResponseDTO.class);
  }

  @Step("Получить животное по его Id={petId}")
  public PetResponseDTO getPetById(Long petId) {
    /*spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));*/

    return RestAssured
        .given(spec.requestSpec(baseURL, pathURL))
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .pathParam("petId", petId)
        .when()
          .get(petIdURL)
        .then()
          .log().ifValidationFails()
          .spec(spec.responseSpec(200))
          .assertThat()
          .body(JsonSchemaValidator
              .matchesJsonSchemaInClasspath("postpetschema.json"))
          .extract().body().jsonPath().getObject(".", PetResponseDTO.class);
  }

  @Step("Добавить новое животное")
  public PetResponseDTO addNewPet(PetRequestDTO petRequestDTO) {
    /*spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));*/

    return RestAssured
        .given(spec.requestSpec(baseURL, pathURL))
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .body(petRequestDTO)
        .when()
          .post(petURL)
        .then()
          .log().ifValidationFails()
          .spec(spec.responseSpec(200))
          .assertThat()
          .body(JsonSchemaValidator
              .matchesJsonSchemaInClasspath("postpetschema.json"))
          .extract().body().as(PetResponseDTO.class);
  }

  @Step("Проверить характеристики созданного животного")
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

  @Step("Удалить животное по его Id={petId}")
  public void deletePetById(Long petId) {
    /*spec.installSpecification(spec.requestSpec(baseURL, pathURL),
        spec.responseSpec(200));*/

    RestAssured
        .given(spec.requestSpec(baseURL, pathURL))
          .contentType(ContentType.JSON)
          .auth().oauth2("")
          .pathParam("petId", petId)
        .when()
          .delete(petIdURL)
        .then()
          .spec(spec.responseSpec(200))
          .statusCode(HttpStatus.SC_OK);
  }

}
