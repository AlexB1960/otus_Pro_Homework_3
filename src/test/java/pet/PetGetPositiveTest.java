package pet;

import com.google.inject.Inject;
import dto.PetResponseDTO;
import extensions.PetExtensions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.Pets;
import java.util.List;

/**
 * Класс содержит позитивные тесты API-метода GET /pet/findByStatus
 * Finds Pets by status
 */
@ExtendWith(PetExtensions.class)
public class PetGetPositiveTest { //extends AbsMethodsPet
  @Inject
  private Pets pet;

  /*Позитивный тест получения списка всех pets со статусом available.
  Проверка получения кода статуса 200 через спецификацию,
  проверка значения available в поле status во всех полученных pets
  и валидация схемы getpetschema.json
  */
  @Test
  public void getPets200() {
    List<PetResponseDTO> pets = pet.getPetsByStatus("available");
    pets.forEach(x -> Assertions.assertEquals("available", x.getStatus()));
  }

}
