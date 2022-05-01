package pl.tsimur.jpatest.service;

import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.UserMiniDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> saveAll(List<User> users);

    Optional<User> find(Integer id);

    List<User> findAll();

    List<User> findAll(String name);

    List<User> findAll(List<Integer> ids);

    List<UserMiniDto> findAllMiniUsers();

    List<User> findAllOlder(Integer age);

    List<User> findAllYounger(Integer age);

    List<User> findAllToUpdatePurchase();

    List<User> findAllUsersWithEnoughMoney(LocalDateTime localDateTime);

    List<User> findAllWithSubs();

    Integer findCountEmployeesWithPensionerAge(String orgName);

    User findOrThrow(Integer id);

    void updateName(Integer id, String name);

    void buySubscription(Integer id, Tariff tariff);

    void updateMonthlyPayment();

    void updateBalance(Integer id, Float balance);

    void updatePurchaseDate(Integer id, LocalDateTime date);

    void delete(Integer id);

    void delete();

}
