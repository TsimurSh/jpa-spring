package pl.timur.jpatest.service

import pl.timur.jpatest.model.Tariff
import pl.timur.jpatest.model.User
import pl.timur.jpatest.model.dto.UserMiniDto
import java.time.LocalDateTime

interface UserService {
    fun save(user: User): User
    fun saveAll(users: List<User>): List<User>
    fun find(id: Int): User?
    fun findAll(): List<User>
    fun findAll(name: String): List<User>
    fun findAll(ids: List<Int>): List<User>
    fun findAllMiniUsers(): List<UserMiniDto>
    fun findAllOlder(age: Int): List<User>
    fun findAllYounger(age: Int): List<User>
    fun findAllToUpdatePurchase(): List<User>
    fun findAllUsersWithEnoughMoney(localDateTime: LocalDateTime): List<User>
    fun findAllWithSubs(): List<User>
    fun findCountEmployeesWithPensionerAge(orgName: String): Int
    fun findOrThrow(id: Int): User
    fun updateName(id: Int, name: String)
    fun buySubscription(id: Int, tariff: Tariff)
    fun updateMonthlyPayment()
    fun updateBalance(id: Int, balance: Float)
    fun updatePurchaseDate(id: Int, date: LocalDateTime)
    fun delete(id: Int)
    fun delete()
}