package pl.timur.jpatest.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table
data class Organization(
    @Id
    val nip: Int,
    val name: String,
    val address: String
) {
    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    @JsonIgnore
    val employees: List<User> = ArrayList()

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    val owner: User? = null
}