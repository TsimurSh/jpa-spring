package pl.timur.jpatest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.timur.jpatest.model.Tariff
import pl.timur.jpatest.model.User
import java.time.LocalDateTime
import java.util.stream.Stream

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findUserById(id: Int): User?
    fun findAllByAgeAfter(age: Int): List<User>
    fun findAllByName(name: String): List<User>

    @Query(
        value = "SELECT * FROM user_account u INNER JOIN subscription s " +
                "  ON u.subscription_name = s.name WHERE u.balance < s.price AND last_payment > :oneMonthAgo ",
        nativeQuery = true
    )
    fun findAllUsersWithEnoughMoneyNative(oneMonthAgo: LocalDateTime): List<User>

    @Query(value = "SELECT u FROM User u WHERE u.balance < u.subscription.price")
    fun findAllUsersWithEnoughMoney(oneMonthAgo: LocalDateTime): List<User>
    fun findAllBySubscriptionName(tariff: Tariff): Stream<User>

    @Query(value = "SELECT * FROM user_account u WHERE subscription_name IS NOT NULL", nativeQuery = true)
    fun findAllWithSubscription(): List<User>
    fun findAllByAgeBefore(age: Int): List<User>
    fun findUserByNameStartingWith(name: String): List<User>

    @Query(
        value = "SELECT count(*) FROM organization org INNER JOIN user_account u " +
                " ON org.nip = u.employer_nip WHERE u.age > 50 and org.name = :orgName", nativeQuery = true
    )
    fun findCountEmployeesWithPensionerAgeInOrganization(orgName: String): Int

    @Query(value = "SELECT u FROM User u WHERE u.lastPayment < :oneMonthAgo AND u.balance > u.subscription.price ")
    fun findAllToUpdatePurchase(oneMonthAgo: LocalDateTime): List<User>

    @Modifying
    @Query(value = "UPDATE User SET balance = :balance WHERE id = :id")
    fun updateUserBalanceById(id: Int, balance: Float)

    @Modifying
    @Query(value = "UPDATE User SET lastPayment = :date WHERE id = :id")
    fun updateUserDateById(id: Int, date: LocalDateTime)

    @Modifying
    @Query(value = "UPDATE User SET name = :name WHERE id = :id")
    fun updateUserNameById(id: Int, name: String)

    @Modifying
    @Query(
        value = """UPDATE User SET subscription.name = :name, balance = :balance, lastPayment =current_timestamp WHERE id = :id"""
    )
    fun updateUserSubscriptionById(id: Int, name: Tariff, balance: Float)

    @Modifying
    @Query(
        value = "UPDATE User SET lastPayment = current_timestamp," +
                " balance = balance - :price WHERE balance >= :price AND subscription.name = :tariff AND lastPayment < :oneMonthAgo"
    )
    fun updateUserPurchase(oneMonthAgo: LocalDateTime, price: Float, tariff: Tariff)
}