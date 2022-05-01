package pl.tsimur.jpatest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Organization {
    @Id
    private Integer nip;

    private String name;

    private String address;

    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> employees;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;
}
