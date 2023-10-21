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
public class ActivityRs {
    private long actionId;
    private long userId;
    private Activity activity;

}
