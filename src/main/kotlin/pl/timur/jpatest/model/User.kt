package pl.timur.jpatest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.ColumnDefault
import pl.timur.jpatest.model.dto.LoginDto
import pl.timur.jpatest.model.dto.UserMiniDto
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.Positive

@Entity
@Table(name = "user_account")
data class User(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Int = 0,
    @Email(message = "Email is mandatory")
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val surname: String? = null,
    @ColumnDefault("0")
    @Positive
    val balance: Float = 0f,
    @Min(0)
    val age: Int? = null,
    val lastPayment: LocalDateTime? = null
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

    fun toLoginDto() = LoginDto(email = email!!, password = password!!)

    fun toUserMiniDto() = UserMiniDto(id, name, surname, age)

    override fun toString(): String {
        return "User(id=$id, email='$email', password='$password', name='$name', " +
                "surname='$surname', balance=$balance, age=$age)"
    }

}