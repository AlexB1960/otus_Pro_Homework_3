package pet;

import com.google.inject.Inject;
import dto.PetResponseDTO;
import extensions.PetExtensions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.PetStore;
import java.util.List;

/**
 * Класс содержит позитивные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
@ExtendWith(PetExtensions.class)
@Epic("Тесты сайта petstore.swagger.io")
@Story("Позитивные тесты")
@DisplayName("Позитивные тесты получения животных сайта petstore.swagger.io")
public class PetGetPositiveTest {
  @Inject
  private PetStore petStore;

  /*Позитивный тест получения списка всех pets со статусом available.
  Проверка получения кода статуса 200 через спецификацию,
  проверка значения available в поле status во всех полученных pets
  и валидация схемы getpetschema.json
  */
  @Test
  @DisplayName("Получение списка животных со статусом=available")
  @Feature("Список животных")
  public void getPets200() {
    List<PetResponseDTO> pets = petStore.getPetsByStatus("available");
    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

}
