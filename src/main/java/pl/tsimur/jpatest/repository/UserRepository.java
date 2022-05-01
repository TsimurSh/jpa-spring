package pl.tsimur.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByAgeAfter(Integer age);

    List<User> findAllByName(String name);

    @Query(value = "SELECT * FROM user_account u INNER JOIN subscription s " +
            "  ON u.subscription_name = s.name WHERE u.balance < s.price AND last_payment > :oneMonthAgo ", nativeQuery = true)
    List<User> findAllUsersWithEnoughMoneyNative(LocalDateTime oneMonthAgo);

    @Query(value = "SELECT u FROM User u WHERE u.balance < u.subscription.price")
    List<User> findAllUsersWithEnoughMoney(LocalDateTime oneMonthAgo);

    Stream<User> findAllBySubscriptionName(Tariff tariff);

    @Query(value = "SELECT * FROM user_account u WHERE subscription_name IS NOT NULL", nativeQuery = true)
    List<User> findAllWithSubscription();

    List<User> findAllByAgeBefore(Integer age);

    List<User> findUserByNameStartingWith(String name);

    @Query(value = "SELECT count(*) FROM organization org INNER JOIN user_account u " +
            " ON org.nip = u.employer_nip WHERE u.age > 50 and org.name = :orgName", nativeQuery = true)
    Integer findCountEmployeesWithPensionerAgeInOrganization(String orgName);

    @Query(value = "SELECT u FROM User u WHERE u.lastPayment < :oneMonthAgo AND u.balance > u.subscription.price ")
    List<User> findAllToUpdatePurchase(LocalDateTime oneMonthAgo);

    @Modifying
    @Query(value = "UPDATE User SET balance = :balance WHERE id = :id")
    void updateUserBalanceById(Integer id, Float balance);

    @Modifying
    @Query(value = "UPDATE User SET lastPayment = :date WHERE id = :id")
    void updateUserDateById(Integer id, LocalDateTime date);

    @Modifying
    @Query(value = "UPDATE User SET name = :name WHERE id = :id")
    void updateUserNameById(Integer id, String name);

    @Modifying
    @Query(value = "UPDATE User SET subscription.name = :name, balance = :balance, lastPayment =" +
            "current_timestamp WHERE id = :id")
    void updateUserSubscriptionById(Integer id, Tariff name, Float balance);

    @Modifying
    @Query(value = "UPDATE User SET lastPayment = current_timestamp," +
            " balance = balance - :price WHERE balance >= :price AND subscription.name = :tariff AND lastPayment < :oneMonthAgo")
    void updateUserPurchase(LocalDateTime oneMonthAgo, Float price, Tariff tariff);


}
