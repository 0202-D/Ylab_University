package io.ylab.mapper.user;

import io.ylab.dto.user.UserRqDto;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDtoRs toDtoRs(User user) {
        return UserDtoRs.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .balance(user.getBalance())
                .build();
    }


    @Override
    public User toEntity(UserRqDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();

    }
}
