package pl.tsimur.jpatest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsersTest() throws Exception {
        mockMvc.perform(
                        get("/user")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value("Super"))
                .andExpect(jsonPath("$[0].surname").value("Man"))
                .andExpect(jsonPath("$[0].balance").doesNotExist())
                .andExpect(jsonPath("$[0].email").doesNotExist())
                .andExpect(jsonPath("$[0].password").doesNotExist())
        ;
    }

    @Test
    void buySubscriptionTest() throws Exception {
        mockMvc.perform(put("/user/-3/INDIVIDUAL"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findByNameTest() throws Exception {
        mockMvc.perform(
                        get("/user/666")
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllYoungUsersTests() throws Exception {
        mockMvc.perform(get("/user/younger/33"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").isNumber())
                .andExpect(jsonPath("$[1].age").isNumber())
        ;
    }
}
