package io.ylab.dto.activity;

import io.ylab.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Dto ответа по активности
 */
public class ActivityRsDto {
    /**
     * идентификатор действия
     */
    private long actionId;
    /**
     * идентификатор пользователя
     */
    private long userId;
    /**
     * enum вид активности
     */
    private Activity activity;

}
