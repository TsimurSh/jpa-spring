package pl.tsimur.jpatest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Subscription {
    @Id
    @Enumerated(STRING)
    private Tariff name;

    private Float price;

    private String title;

    @OneToMany(mappedBy = "subscription", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> users = new ArrayList<>();

}
