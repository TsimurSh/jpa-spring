package pl.tsimur.jpatest.mapper;

import org.mapstruct.Mapper;
import pl.tsimur.jpatest.model.User;
import pl.tsimur.jpatest.model.dto.LoginDto;
import pl.tsimur.jpatest.model.dto.UserMiniDto;

import java.util.List;

@Mapper
public interface UserMapper {

    LoginDto toLoginDto(User user);

    User fromLoginDto(LoginDto user);

    List<UserMiniDto> toUserMiniDto(List<User> users);

    UserMiniDto toUserMiniDto(User user);


}
