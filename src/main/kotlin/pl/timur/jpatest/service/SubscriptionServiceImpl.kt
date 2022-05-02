package pl.timur.jpatest.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.timur.jpatest.model.Subscription
import pl.timur.jpatest.model.Tariff
import pl.timur.jpatest.repository.SubscriptionRepository

@Service
class SubscriptionServiceImpl(
    private val subscriptionRepository: SubscriptionRepository
) : SubscriptionService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun find(name: String): Subscription? {
        val subscription = subscriptionRepository.findByName(Tariff.valueOf(name))
        log.info("Found subscription with $name name")
        return subscription
    }

    override fun findTitle(title: String): Subscription? {
        val subscription = subscriptionRepository.findByTitle(title)
        log.info("""Found subscription with title: "$title"""")
        return subscription
    }

    override fun find(price: Float): Subscription? {
        val subscription = subscriptionRepository.findByPrice(price)
        log.info("Found subscription with price $price pln ")
        return subscription
    }

    @Transactional
    override fun updateTitle(name: Tariff, title: String) {
        subscriptionRepository.updateByName(name, title)
        log.info("Subscription   with name: ${name.name} have new title; $title")
    }

    @Transactional
    override fun updatePrice(title: String, price: Float) {
        subscriptionRepository.updateByTitle(title, price)
        log.info("Subscription with title: $title have new price = $price")
    }
}