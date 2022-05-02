package pl.timur.jpatest.mapper

import org.mapstruct.Mapper
import pl.timur.jpatest.model.User
import pl.timur.jpatest.model.dto.LoginDto
import pl.timur.jpatest.model.dto.UserMiniDto

@Mapper
interface UserMapper {
    fun toLoginDto(user: User): LoginDto
    fun fromLoginDto(user: LoginDto): User
    fun toUserMiniDto(users: List<User>): List<UserMiniDto>
    fun toUserMiniDto(user: User): UserMiniDto
}