package io.ylab.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * dto запроса на регистрацию и аутентификацию пользователя
 */
@Schema(description = "Основная информация по юзеру для регистрации и аутентификации")
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
