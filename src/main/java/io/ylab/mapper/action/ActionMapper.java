package io.ylab.mapper.action;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.model.Action;

public interface ActionMapper {
    ActivityRsDto toDtoRs(Action action);
}
