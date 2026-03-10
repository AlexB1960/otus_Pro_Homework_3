package pet;

import common.AbsMethodsPetsDTO;
import dto.PetResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Класс содержит позитивные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
public class PetGetPositiveTest extends AbsMethodsPetsDTO {

  /*Позитивный тест получения списка всех pets со статусом available.
  Проверка получения кода статуса 200 через спецификацию,
  проверка значения available в поле status во всех полученных pets
  и валидация схемы getpetschema.json
  */
  @Test
  public void getPets200() {
    List<PetResponseDTO> pets = getPetsByStatus("available");
    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

}
