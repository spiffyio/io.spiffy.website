package io.spiffy.notification.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.notification.input.GetUnreadCountInput;
import io.spiffy.common.api.notification.output.GetUnreadCountOutput;
import io.spiffy.notification.service.AlertService;

@RequestMapping("/api/notification/getunreadcount")
public class GetUnreadCountAPI extends API<GetUnreadCountInput, GetUnreadCountOutput, AlertService> {

    @Inject
    public GetUnreadCountAPI(final AlertService service) {
        super(GetUnreadCountInput.class, service);
    }

    protected GetUnreadCountOutput api(final GetUnreadCountInput input) {
        return new GetUnreadCountOutput(service.getUnreadCount(input.getAccountId()));
    }
}
