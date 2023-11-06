package io.ylab.dto.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Основная информация активности пользователя")
/**
 * Dto ответа по активности
 */
public class ActivityRsDto {
    /**
     * идентификатор действия
     */
    @Schema(name = "actionId",description = "Идентификатор действия")
    private long actionId;
    /**
     * идентификатор пользователя
     */
    @Schema(name = "userId",description = "Идентификатор пользователя")
    private long userId;
    /**
     * enum вид активности
     */
    @Schema(name = "activity",description = "Вид активности")
    private Activity activity;

}
