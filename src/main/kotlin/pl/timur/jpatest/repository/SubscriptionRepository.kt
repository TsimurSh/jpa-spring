package pl.timur.jpatest.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.timur.jpatest.model.Subscription
import pl.timur.jpatest.model.Tariff

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, Tariff> {
    fun findByName(name: Tariff): Subscription?
    fun findByPrice(price: Float): Subscription?
    fun findByTitle(title: String): Subscription?

    @Modifying
    @Query(value = "UPDATE Subscription SET title = :title WHERE name = :name")
    fun updateByName(name: Tariff, title: String)

    @Modifying
    @Query(value = "UPDATE Subscription SET price=:price WHERE title = :title")
    fun updateByTitle(title: String, price: Float)
}