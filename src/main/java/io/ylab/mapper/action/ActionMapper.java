package io.ylab.mapper.action;

import io.ylab.dto.activity.ActivityRs;
import io.ylab.model.Action;

public interface ActionMapper {
    ActivityRs toDtoRs(Action action);
}
