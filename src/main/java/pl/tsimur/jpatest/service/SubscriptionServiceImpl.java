package pl.tsimur.jpatest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tsimur.jpatest.model.Subscription;
import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.repository.SubscriptionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription find(String name) {
        Subscription subscription =
                subscriptionRepository.findByName(Tariff.valueOf(name));
        log.info("Found subscription with {} name", name);
        return subscription;
    }

    @Override
    public Subscription findTitle(String title) {
        Subscription subscription =
                subscriptionRepository.findByTitle(title);
        log.info("Found subscription with title: \"{}\" ", title);
        return subscription;
    }

    @Override
    public Subscription find(Float price) {
        Subscription subscription =
                subscriptionRepository.findByPrice(price);
        log.info("Found subscription with price {} pln ", price);
        return subscription;
    }

    @Override
    @Transactional
    public void updateTitle(Tariff name, String title) {
        subscriptionRepository.updateByName(name, title);
        log.info("Subscription with name: {} have new title \"{}\"", name.toString(), title);
    }

    @Override
    @Transactional
    public void updatePrice(String title, Float price) {
        subscriptionRepository.updateByTitle(title, price);
        log.info("Subscription with title: {} have new price = {}", title, price);
    }

}
