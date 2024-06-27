package pl.timur.jpatest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table
data class Role(
    @Id
    @Enumerated(EnumType.STRING)
    val role: Scope
) {
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    val users: List<User> = ArrayList()
}
