package pl.timur.jpatest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.Positive

@Entity
@Table(name = "user_account")
class User(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Int,
    val email: @Email(message = "Email is mandatory") String,
    val password: String,
    val name: String,
    val surname: String,
    @ColumnDefault("0")
    val balance: @Positive Float,
    val age: @Min(0) Int,
    val lastPayment: LocalDateTime
) {
    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    val organization: Organization? = null

    @ManyToOne
    @JoinColumn(name = "employer_nip")
    @JsonIgnore
    val employer: Organization? = null

    @ManyToOne
    @JoinColumn(name = "subscription_name")
    @JsonIgnore
    val subscription: Subscription? = null

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role")]
    )
    val roles: List<Role> = ArrayList()

    override fun toString(): String {
        return "User(id=$id, email='$email', password='$password', name='$name', " +
                "surname='$surname', balance=$balance, age=$age)"
    }

}