package pl.timur.jpatest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Table
@Entity
data class Subscription(
    @Id
    @Enumerated(EnumType.STRING)
    val name: Tariff,
    val price: Float,
    val title: String
) {
    @OneToMany(mappedBy = "subscription", fetch = FetchType.EAGER)
    @JsonIgnore
    val users: List<User> = ArrayList()
}
