package pl.tsimur.jpatest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMiniDto {

    private Integer id;
    private String name;
    private String surname;
    private Integer age;
}
