package pl.tsimur.jpatest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Email(message = "Email is mandatory")
    private String email;
    private String password;
    private String name;
    private String surname;
    @ColumnDefault("0")
    @Positive
    private Float balance;
    @Min(0)
    private Integer age;

    private LocalDateTime lastPayment;

    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "employer_nip")
    @JsonIgnore
    private Organization employer;
    @ManyToOne
    @JoinColumn(name = "subscription_name")
    @JsonIgnore
    private Subscription subscription;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role"))
    private List<Role> roles = new ArrayList<>();

    public User(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", balance=" + balance +
                ", age=" + age +
                ", lastPayment=" + lastPayment +
                '}';
    }
}
