package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Value
@Getter
public class Category {
  Integer id;
  String name;
}
