package dto;

import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
//@Getter
public class Tag {
  Long id;
  String name;
}
