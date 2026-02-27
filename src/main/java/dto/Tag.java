package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

@Builder
@Data
public class Tag {
  Integer id;
  String name;
}
