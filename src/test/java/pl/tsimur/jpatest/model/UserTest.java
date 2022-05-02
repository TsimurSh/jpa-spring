package pl.tsimur.jpatest.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.timur.jpatest.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class UserTest {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void createUser() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setPassword("SECRET");
        System.out.println("User with id & password : " + user);

        String result = "";

        result += "";

        var json = mapper.writeValueAsString(user);

        assertEquals("\"{\"id\":\"1\", \"password\":\"SECRET\"}", json);
    }
}