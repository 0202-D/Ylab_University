package io.ylab.mapper.user;

import io.ylab.dto.user.UserDtoRq;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDtoRs toDtoRs(User user);
    User toEntity(UserDtoRq userDto);
}
