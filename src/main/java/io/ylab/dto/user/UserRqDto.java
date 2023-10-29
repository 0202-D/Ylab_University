package io.ylab.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * dto запроса на регистрацию и аутентификацию пользователя
 */
public class UserRqDto {
    /**
     * имя пользователя
     */
    @Size(min=2,message = "Имя должно быть больше двух символов")
    private String userName;
    /**
     * пароль пользователя
     */
    @NotBlank
    private String password;
}
