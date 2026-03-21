package pet;

import common.AbsMethodsPet;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import org.junit.jupiter.api.Test;

/**
 * Класс содержит позитивные тесты API-метода POST /pet/findByStatus
 * Add a new pet to the store
 */
public class PetPostPositiveTest extends AbsMethodsPet {

  /*Позитивный тест создания одного pet со значениями во всех полях.
  Проверка получения кода статуса 200 через спецификацию,
  получение значений полей созданного pet через метод getPetById,
  проверка полученных значений через soft assert в методе assertCreatedPet
  и валидация схемы postpetschema.json
  Созданный в тесте pet удаляется в конце теста методом deletePetById
  */
  @Test
  public void createPet200() {
    PetRequestDTO petRequestDTO = PetRequestDTO.builder()
        .id(0L)
        .category(setCategory("dog"))
        .name("Такса")
        .photoUrls(setPhotoUrl("https://images/first.jpg", "https://images/second.jpg"))
        .tags(setTags("Tag N1", "Tag N2", "Tag N3", "Tag N4"))
        .status("available")
        .build();

    PetResponseDTO petResponseDTO = addNewPet(petRequestDTO);
    deletedList.add(petResponseDTO.getId());
    PetResponseDTO createdPet = getPetById(petResponseDTO.getId());
    assertCreatedPet(petRequestDTO, createdPet);
    //deletePetById(petResponseDTO.getId());
  }

}
