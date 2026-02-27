package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

@Builder
@Data
public class Category {
  Integer id;
  String name;
}
