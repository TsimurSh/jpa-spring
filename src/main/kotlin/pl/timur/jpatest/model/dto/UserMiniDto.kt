package pl.timur.jpatest.model.dto


data class UserMiniDto(
    val id: Int,
    val name: String? = null,
    val surname: String? = null,
    val age: Int? = null
)