package pl.tsimur.jpatest.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.tsimur.jpatest.exception.NotEnoughMoneyException;
import pl.tsimur.jpatest.model.Tariff;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.UserMiniDto;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    private static final User userNewForTest = User.builder()
            .name("Andrey")
            .surname("Ivanov")
            .email("rubicon@tests.com")
            .age(31)
            .balance(100f)
            .build();
    private static final User userNewForTest1 = User.builder()
            .name("Andrey")
            .surname("Ivanov")
            .email("rubruby@test.com")
            .age(11)
            .balance(1.1f)
            .build();
    private static List<User> users = new ArrayList<>();
    @Autowired
    private UserServiceImpl userService;

    @BeforeAll
    void init() {
        users = userService.findAll();
    }

    @AfterAll
    void cleanUp() {
        userService.saveAll(users);
    }


    @TestFactory
    @RepeatedTest(10)
    void createTest() {
        User user = userService.save(userNewForTest);
        System.out.println("Autoincrement user id: " + user);
        final Integer id = user.getId();
        assertNotNull(id);
        assertNotNull(user.getName());
        userService.delete(id);

        System.out.println("user with id: " + user + " removed");
    }

    @Test
    void findAllTest() {
        List<UserMiniDto> usersList = userService.findAllMiniUsers();
        assertNotNull(usersList.get(0));
    }

    @Test
    void findOlderTest() {
        Integer age = 20;
        List<User> usersList = userService.findAllOlder(age);
        assertTrue(usersList.get(0).getAge() >= age);
    }

    @Test
    void findYoungerTest() {
        Integer age = 40;
        List<User> usersList = userService.findAllYounger(age);
        assertTrue(usersList.get(0).getAge() <= age);
    }

    @Test
    void updateNameTest() {
        String searchingName = "Lena";
        List<User> usersWithName = userService.findAll(searchingName);
        String oldName = usersWithName.get(0).getName();
        assertEquals(searchingName, oldName);

        Integer id = usersWithName.get(0).getId();

        String newName = "Arsen";
        userService.updateName(id, newName);
        assertNotNull(userService.findAll(newName));
        Optional<User> usersWithNewName = userService.find(id);
        System.out.println(usersWithNewName.get().getName());
    }

    @Test
    void buySubscription__WhenNotExists__Test() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.buySubscription(-666, Tariff.INDIVIDUAL);
        });
    }

    @Test
    void buySubscriptionTest() {
        userNewForTest.setEmail(UUID.randomUUID() + "@email.com");

        User userBeforeUpdate = userService.save(userNewForTest);

        assertNull(userBeforeUpdate.getSubscription());

        Integer id = userBeforeUpdate.getId();

        userService.buySubscription(id, Tariff.INDIVIDUAL);

        User userAfterUpdate = userService.findOrThrow(id);

        assertNotEquals(userBeforeUpdate.getBalance(), userAfterUpdate.getBalance());

        assertNotNull(userAfterUpdate.getSubscription());

        userService.delete(id);
    }

    @Test
    void findCountPensionersInOrgWithNamesTest() {
        Integer count = userService
                .findCountEmployeesWithPensionerAge("Apollo");
        assertTrue(
                count > 0);
        System.out.println(count);
    }

    @Test
    void buySubscription__WhenNotMoneyTest() {
        User userBeforeUpdate = userService.save(userNewForTest1);
        NotEnoughMoneyException noMoney = Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
            userService
                    .buySubscription(userBeforeUpdate.getId(), Tariff.INDIVIDUAL);
        });

        String message = noMoney.getMessage();

        assertTrue(message.contains("money"));
    }

    @Test
    void findAllUsersWithSubsTest() {
        List<User> userList = userService.findAllWithSubs();
        assertNotNull(userList.get(0));
        assertNotNull(userList.get(0).getSubscription());
    }

    @Test
    void findAllToUpdatePurchaseTest() {
        List<User> allToUpdatePurchase = userService.findAllToUpdatePurchase();
        assertFalse(allToUpdatePurchase.isEmpty());

    }

    @RepeatedTest(2)
    @Transactional
    void updatePurchaseLastDatePaymentTest() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<User> poorUsers = userService.findAllUsersWithEnoughMoney(oneMonthAgo);

        assertFalse(poorUsers.isEmpty());

        User user = poorUsers.get(0);
        assertNotNull(user.getBalance());

        assertTrue(user.getBalance() < user.getSubscription().getPrice());

        List<User> allToUpdatePurchase = userService.findAllToUpdatePurchase();
        assertFalse(allToUpdatePurchase.isEmpty());

        userService.updateMonthlyPayment();

        List<User> allToUpdatedUsers = userService.findAllToUpdatePurchase();
        assertTrue(allToUpdatedUsers.isEmpty());
    }
}
