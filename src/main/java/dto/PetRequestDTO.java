package dto;

import lombok.*;
import java.util.ArrayList;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
//@Getter
public class PetRequestDTO {
  Long id;
  Category category;
  String name;
  ArrayList<String> photoUrls;
  ArrayList<Tag> tags;
  String status;
}
