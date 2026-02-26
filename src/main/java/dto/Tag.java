package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Value
@Getter
public class Tag {
  Integer id;
  String name;
}
