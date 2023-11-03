package io.ylab.mapper.action;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.model.Action;
import org.springframework.stereotype.Component;

@Component
public class ActionMapperImpl implements ActionMapper{
    @Override
    public ActivityRsDto toDtoRs(Action action) {
        return ActivityRsDto.builder()
                .actionId(action.getActionId())
                .userId(action.getUser().getUserId())
                .activity(action.getActivity())
                .build();
    }
}
