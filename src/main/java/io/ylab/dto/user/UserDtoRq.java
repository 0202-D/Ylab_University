package io.ylab.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRq {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
