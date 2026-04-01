package pet;

import com.google.inject.Inject;
import dto.PetRequestDTO;
import dto.PetResponseDTO;
import extensions.PetExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pets.PetStore;

/**
 * Класс содержит позитивные тесты API-метода POST /pet/findByStatus
 * Add a new pet to the store
 */
@ExtendWith(PetExtensions.class)
public class PetPostPositiveTest {// extends AbsMethodsPet
  @Inject
  private PetStore petStore;

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
        .category(petStore.setCategory("dog"))
        .name("Такса")
        .photoUrls(petStore.setPhotoUrl("https://images/first.jpg", "https://images/second.jpg"))
        .tags(petStore.setTags("Tag N1", "Tag N2", "Tag N3", "Tag N4"))
        .status("available")
        .build();

    PetResponseDTO petResponseDTO = petStore.addNewPet(petRequestDTO);
    petStore.savePet(petResponseDTO.getId());
    PetResponseDTO createdPet = petStore.getPetById(petResponseDTO.getId());
    petStore.assertCreatedPet(petRequestDTO, createdPet);
  }

}
