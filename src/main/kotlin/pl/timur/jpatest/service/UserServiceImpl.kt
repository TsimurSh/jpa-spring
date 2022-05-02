package pl.timur.jpatest.service

import org.mapstruct.factory.Mappers
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.timur.jpatest.exception.NotEnoughMoneyException
import pl.timur.jpatest.exception.NotFoundException
import pl.timur.jpatest.mapper.UserMapper
import pl.timur.jpatest.model.Subscription
import pl.timur.jpatest.model.Tariff
import pl.timur.jpatest.model.User
import pl.timur.jpatest.model.dto.UserMiniDto
import pl.timur.jpatest.repository.SubscriptionRepository
import pl.timur.jpatest.repository.UserRepository
import java.time.LocalDateTime


@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository
) : UserService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        private val MAPPER = Mappers.getMapper(UserMapper::class.java)
    }

    @Transactional
    @Scheduled(cron = "@hourly")
    override fun updateMonthlyPayment() {
        log.info("🥎 Run updating monthly payment for users with subscription")
        val date = LocalDateTime.now().minusMonths(1)
        val subscriptions = subscriptionRepository.findAll()
        findAllUsersWithoutMoneyForPurchase(date)
        chargeAll(date, subscriptions)
        log.info("🤸 Updating is completed ")
    }

    private fun chargeAll(date: LocalDateTime, subscriptions: List<Subscription>) {
        log.info("🥊 Purchase search... ")
        subscriptions.forEach {
            repository
                .updateUserPurchase(date, it.price, it.name)
        }
    }

    private fun findAllUsersWithoutMoneyForPurchase(date: LocalDateTime): List<User> {
        val users = findAllUsersWithEnoughMoney(date)
        users.forEach {
            log.info(
                "Poor {} with balance {} and subs. {}", it.id, it.balance,
                it.subscription!!.name
            )
        }
        return users
    }

    @Transactional
    override fun buySubscription(id: Int, tariff: Tariff) {
        val buyer = repository.getById(id)
        val purchase = subscriptionRepository.findByName(tariff)
        log.info("🕊 Start buying subscription {} for user: {}", purchase!!.name, buyer.id)
        val isEnoughMoney = buyer.balance >= purchase.price
        log.info("🌿 Check user's balance. Is enough money = {}", isEnoughMoney)
        if (isEnoughMoney) {
            val newBalance = buyer.balance - purchase.price
            val purchaseName = purchase.name
            repository.updateUserSubscriptionById(id, purchaseName, newBalance)
            log.info("🌵Subscription {} is bought", purchaseName)
        } else throw NotEnoughMoneyException(buyer.balance)
    }

    override fun save(user: User): User {
        log.info("Saving user...")
        val userSaved = repository.save(user)
        log.info("SUCCESS: user saved: {}", user)
        return userSaved
    }

    override fun saveAll(users: List<User>): List<User> {
        log.info("Saving all users...")
        val usersList = repository.saveAll(users)
        log.info("SUCCESS: all users saved: {}", users)
        return usersList
    }

    override fun findCountEmployeesWithPensionerAge(orgName: String): Int {
        val count = repository.findCountEmployeesWithPensionerAgeInOrganization(orgName)
        log.info("Count of employees with pensioner age = {}", count)
        return count
    }

    override fun findOrThrow(id: Int): User = find(id) ?: throw NotFoundException(
        "ID=$id"
    )

    override fun find(id: Int): User? = repository.findUserById(id)

    override fun findAll(name: String): List<User> {
        val users = repository.findAllByName(name)
        log.info("🏏 Found [ {} ] users with name : {}", users.size, name)
        return users
    }

    override fun findAll(ids: List<Int>): List<User> = repository.findAllById(ids)

    override fun findAll(): List<User> = repository.findAll()

    override fun findAllMiniUsers(): List<UserMiniDto> {
        val users = repository.findAll()
        val usersMimi = MAPPER.toUserMiniDto(users)
        log.info("Found {} usersMimi", users.size)
        return usersMimi
    }

    override fun findAllOlder(age: Int): List<User> = repository.findAllByAgeAfter(age)

    override fun findAllYounger(age: Int): List<User> {
        return repository.findAllByAgeBefore(age)
    }

    override fun findAllToUpdatePurchase(): List<User> {
        val minusMonths = LocalDateTime.now().minusMonths(1)
        return repository.findAllToUpdatePurchase(minusMonths)
    }

    override fun findAllUsersWithEnoughMoney(localDateTime: LocalDateTime): List<User> {
        val poorUsers = repository.findAllUsersWithEnoughMoney(localDateTime)
        log.info("🥊 Printing poor users: " + poorUsers.size)
        return poorUsers
    }

    override fun findAllWithSubs(): List<User> {
        val usersWithSubscription = repository.findAllWithSubscription()
        log.info("Found {} users with bought subscription", usersWithSubscription.size)
        return usersWithSubscription
    }

    @Transactional
    override fun updateName(id: Int, name: String) {
        repository.updateUserNameById(id, name)
    }

    @Transactional
    override fun updateBalance(id: Int, balance: Float) {
        repository.updateUserBalanceById(id, balance)
    }

    @Transactional
    override fun updatePurchaseDate(id: Int, date: LocalDateTime) {
        repository.updateUserDateById(id, date)
    }

    override fun delete(id: Int) {
        repository.deleteById(id)
    }

    override fun delete() {
        repository.deleteAll()
    }


}