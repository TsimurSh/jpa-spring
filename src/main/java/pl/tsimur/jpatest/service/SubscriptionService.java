package pl.tsimur.jpatest.service;

import pl.tsimur.jpatest.model.Subscription;
import pl.tsimur.jpatest.model.Tariff;

public interface SubscriptionService {

    Subscription find(String name);

    Subscription findTitle(String title);

    Subscription find(Float price);

    void updateTitle(Tariff name, String title);

    void updatePrice(String title, Float price);
}
