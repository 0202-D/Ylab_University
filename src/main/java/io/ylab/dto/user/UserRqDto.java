package io.ylab.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * dto запроса на регистрацию и аутентификацию пользователя
 */
public class UserRqDto {
    /**
     * имя пользователя
     */
    @NotBlank
    private String userName;
    /**
     * пароль пользователя
     */
    @NotBlank
    private String password;
}
