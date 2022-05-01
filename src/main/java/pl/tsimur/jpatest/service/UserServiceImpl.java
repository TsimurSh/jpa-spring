package pl.tsimur.jpatest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tsimur.jpatest.exception.NotEnoughMoneyException;
import pl.tsimur.jpatest.exception.NotFoundException;
import pl.tsimur.jpatest.mapper.UserMapper;
import pl.tsimur.jpatest.model.Subscription;
import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.UserMiniDto;
import pl.tsimur.jpatest.repository.SubscriptionRepository;
import pl.tsimur.jpatest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    private final UserRepository repository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    @Scheduled(cron = "@hourly")
    public void updateMonthlyPayment() {
        log.info("🥎 Run updating monthly payment for users with subscription");
        LocalDateTime date = now().minusMonths(1);
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        findAllUsersWithoutMoneyForPurchase(date);

        chargeAll(date, subscriptions);
        log.info("🤸 Updating is completed ");
    }

    private void chargeAll(LocalDateTime date, List<Subscription> subscriptions) {
        log.info("🥊 Purchase search... ");
        subscriptions.forEach(sub -> repository
                .updateUserPurchase(date, sub.getPrice(), sub.getName()));
    }

    private List<User> findAllUsersWithoutMoneyForPurchase(LocalDateTime date) {
        List<User> users = findAllUsersWithEnoughMoney(date);
        users.forEach(it -> log.info("Poor {} with balance {} and subs. {}", it.getId(), it.getBalance(),
                it.getSubscription().getName()));
        return users;
    }

    @Override
    @Transactional
    public void buySubscription(Integer id, Tariff tariff) {
        User buyer = repository.getById(id);
        Subscription purchase = subscriptionRepository.findByName(tariff);
        log.info("🕊 Start buying subscription {} for user: {}", purchase.getName(), buyer.getId());
        boolean isEnoughMoney = buyer.getBalance() >= purchase.getPrice();
        log.info("🌿 Check user's balance. Is enough money = {}", isEnoughMoney);
        if (isEnoughMoney) {
            Float newBalance = buyer.getBalance() - purchase.getPrice();
            Tariff purchaseName = purchase.getName();
            repository.updateUserSubscriptionById(id, purchaseName, newBalance);
            log.info("🌵Subscription {} is bought", purchaseName);
        } else throw new NotEnoughMoneyException(buyer.getBalance());
    }

    @Override
    public User save(User user) {
        log.info("Saving user...");
        User userSaved = repository.save(user);
        log.info("SUCCESS: user saved: {}", user);
        return userSaved;
    }

    @Override
    public List<User> saveAll(List<User> users) {
        log.info("Saving all users...");
        List<User> usersList = repository.saveAll(users);

        log.info("SUCCESS: all users saved: {}", users);
        return usersList;
    }

    @Override
    public Integer findCountEmployeesWithPensionerAge(String orgName) {
        Integer count = repository.findCountEmployeesWithPensionerAgeInOrganization(orgName);
        log.info("Count of employees with pensioner age = {}", count);
        return count;
    }

    @Override
    public User findOrThrow(Integer id) {
        return find(id)
                .orElseThrow(() -> new NotFoundException("ID=" + id));
    }

    @Override
    public Optional<User> find(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll(String name) {
        List<User> users = repository.findAllByName(name);
        log.info("🏏 Found [ {} ] users with name : {}", users.size(), name);
        return users;
    }


    @Override
    public List<User> findAll(List<Integer> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UserMiniDto> findAllMiniUsers() {
        List<User> users = repository.findAll();
        List<UserMiniDto> usersMimi = MAPPER.toUserMiniDto(users);
        log.info("Found {} usersMimi", users.size());
        return usersMimi;
    }

    @Override
    public List<User> findAllOlder(Integer age) {
        return repository.findAllByAgeAfter(age);
    }

    @Override
    public List<User> findAllYounger(Integer age) {
        return repository.findAllByAgeBefore(age);
    }

    @Override
    public List<User> findAllToUpdatePurchase() {
        LocalDateTime minusMonths = now().minusMonths(1);
        List<User> poorUsers = repository.findAllToUpdatePurchase(minusMonths);
        return poorUsers;
    }


    @Override
    public List<User> findAllUsersWithEnoughMoney(LocalDateTime localDateTime) {
        List<User> poorUsers = repository.findAllUsersWithEnoughMoney(localDateTime);
        log.info("🥊 Printing poor users: " + poorUsers.size());
        return poorUsers;
    }

    @Override
    public List<User> findAllWithSubs() {
        List<User> usersWithSubscription = repository.findAllWithSubscription();
        log.info("Found {} users with bought subscription", usersWithSubscription.size());
        return usersWithSubscription;
    }

    @Override
    @Transactional
    public void updateName(Integer id, String name) {
        repository.updateUserNameById(id, name);
    }

    @Override
    @Transactional
    public void updateBalance(Integer id, Float balance) {
        repository.updateUserBalanceById(id, balance);
    }

    @Override
    @Transactional
    public void updatePurchaseDate(Integer id, LocalDateTime date) {
        repository.updateUserDateById(id, date);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete() {
        repository.deleteAll();
    }
}
