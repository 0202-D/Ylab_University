package io.ylab.mapper.action;

import io.ylab.dto.activity.ActivityRs;
import io.ylab.model.Action;

public class ActionMapperImpl implements ActionMapper{
    @Override
    public ActivityRs toDtoRs(Action action) {
        return ActivityRs.builder()
                .actionId(action.getActionId())
                .userId(action.getUser().getUserId())
                .activity(action.getActivity())
                .build();
    }
}
