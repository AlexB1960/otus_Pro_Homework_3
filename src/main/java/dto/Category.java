package dto;

import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
//@Getter
public class Category {
  Long id;
  String name;
}
