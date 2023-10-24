package io.ylab.mapper.user;

import io.ylab.dto.user.UserDtoRq;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.model.User;

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
    public User toEntity(UserDtoRq userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();

    }
}
