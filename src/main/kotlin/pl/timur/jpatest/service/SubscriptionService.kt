package pl.timur.jpatest.service

import pl.timur.jpatest.model.Subscription
import pl.timur.jpatest.model.Tariff

interface SubscriptionService {
    fun find(name: String): Subscription?
    fun findTitle(title: String): Subscription?
    fun find(price: Float): Subscription?
    fun updateTitle(name: Tariff, title: String)
    fun updatePrice(title: String, price: Float)
}