package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

@Builder
@Value
@Getter
public class Category {
  Long id;
  String name;
}
