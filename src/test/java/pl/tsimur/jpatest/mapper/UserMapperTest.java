package pl.tsimur.jpatest.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.LoginDto;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final static User USER = User.builder()
            .email("EMAIL")
            .password("PASSWORD")
            .build();

    private final static LoginDto LOGIN_DTO = new LoginDto("A@A.COM", "B");

    private final static UserMapper MAPPER = Mappers.getMapper(UserMapper.class);


    @Test
    void toLoginDto() {
        LoginDto loginDto = MAPPER.toLoginDto(USER);

        assertEquals(USER.getEmail(), loginDto.getEmail());
        assertNotEquals(LOGIN_DTO.getEmail(), loginDto.getEmail());


        assertEquals(USER.getPassword(), loginDto.getPassword());
        assertNotEquals(LOGIN_DTO.getPassword(), loginDto.getPassword());
    }

    @Test
    void fromLoginDto() {
        User user = MAPPER.fromLoginDto(LOGIN_DTO);
        assertNotNull(user);
        assertEquals(LOGIN_DTO.getEmail(), user.getEmail());
        assertEquals(LOGIN_DTO.getPassword(), user.getPassword());
    }

    @Test
    void fromLoginDto2Test() {
        LoginDto dto = new LoginDto();

        BeanUtils.copyProperties(LOGIN_DTO, dto);

        assertEquals(LOGIN_DTO.getEmail(), dto.getEmail());
        assertEquals(LOGIN_DTO.getPassword(), dto.getPassword());
    }

}
