package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;

@Builder
@Data
public class PetResponseDTO {
  Integer id;
  Category category;
  String name;
  ArrayList<String> photoUrls;
  ArrayList<Tag> tags;
  String status;
}
