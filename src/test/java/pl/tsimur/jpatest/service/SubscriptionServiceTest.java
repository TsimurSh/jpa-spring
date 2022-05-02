package pl.tsimur.jpatest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.timur.jpatest.model.Subscription;
import pl.timur.jpatest.model.Tariff;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Test
    void findByTitleTest() {
        Subscription subs = subscriptionServiceImpl.findTitle("Family plan");
        assertNotNull(subs);
    }

    @Test
    void findByPriceTest() {
        Subscription subs = subscriptionServiceImpl.find(15f);
        assertNotNull(subs);
    }

    @Test
    void findByNameTest() {
        Subscription subs = subscriptionServiceImpl.find("FAMILY");
        assertNotNull(subs);
    }

    @Test
    void updateTitleTest() {
        String title = "Family plan";
        String newTitle = "Family gay";
        Subscription subscription = subscriptionServiceImpl.findTitle(title);
        String oldTitle = subscription.getTitle();
        assertEquals(title, oldTitle);
        Tariff name = subscription.getName();
        subscriptionServiceImpl.updateTitle(name, newTitle);
        assertNotNull(subscriptionServiceImpl.findTitle(newTitle));
        subscriptionServiceImpl.updateTitle(name, oldTitle);
    }

    @Test
    void updatePriceTest() {
        String title = "Family plan";
        Subscription subscription = subscriptionServiceImpl.findTitle(title);
        Float oldPrice = subscription.getPrice();
        subscriptionServiceImpl.updatePrice(title, 33.33f);
        assertNull(subscriptionServiceImpl.find(oldPrice));
        subscriptionServiceImpl.updatePrice(title, oldPrice);
        Subscription rollBackPrice = subscriptionServiceImpl.findTitle(title);
        assertEquals(oldPrice, rollBackPrice.getPrice());
    }
}

