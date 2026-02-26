package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;

@Builder
@Value
@Getter
public class PetResponceDTO {
  Integer id;
  Category category;
  String name;
  ArrayList<String> photoUrls;
  ArrayList<Tag> tags;
  String status;
}
