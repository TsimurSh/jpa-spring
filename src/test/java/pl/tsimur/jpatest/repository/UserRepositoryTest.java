package pl.tsimur.jpatest.repository;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.timur.jpatest.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class UserRepositoryTest {

    private static Integer id;
    private final User user1 = User.builder().name("Tarantul").surname("Ivanov")
            .age(34).email("tarantul-666@gmail.com")
            .password("ouhg938yg93ojkgrh3984ygu20jvn39420uefpj").build();
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(2)
    void createUserTest() {
        assertNull(id);
        User userSaved = userRepository.save(user1);
        System.out.println("Created userSaved: " + userSaved);
        System.out.println("User id = " + id);

        id = userSaved.getId();

        assertNotNull(id);
        userRepository.deleteById(id);
    }


    @Test
    @Order(3)
    void findUserByAgeAfterTest() {
        List<User> users = userRepository.findAllByAgeAfter(18);

        assertTrue(users.size() > 4);
    }

    @Test
    @Order(4)
    void findUserByAgeBeforeTest() {
        List<User> users = userRepository.findAllByAgeBefore(50);

        assertFalse(users.isEmpty());
    }

    @Test
    @Order(5)
    void findUserByNameStartingWithTest() {
        List<User> users = userRepository.findUserByNameStartingWith("T");
        assertEquals("Tsima", users.get(0).getName());
    }

    @Test
    @Order(6)
    void getUserByIdTest() {
        Optional<User> user = userRepository.findById(-2);
        assertTrue(user.isPresent());
    }

    @Test
    @Order(7)
    void getUserById___WhenNotExistsTest() {
        Optional<User> userOpt = userRepository.findById(666);
        assertFalse(userOpt.isPresent());
    }
}

