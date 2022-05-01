package pl.tsimur.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tsimur.jpatest.model.Subscription;
import pl.tsimur.jpatest.model.Tariff;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Tariff> {

    Subscription findByName(Tariff name);

    Subscription findByPrice(Float price);

    Subscription findByTitle(String title);

    @Modifying
    @Query(value = "UPDATE Subscription SET title = :title WHERE name = :name")
    void updateByName(Tariff name, String title);

    @Modifying
    @Query(value = "UPDATE Subscription SET price=:price WHERE title = :title")
    void updateByTitle(String title, Float price);
}
