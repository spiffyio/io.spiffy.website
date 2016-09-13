package io.spiffy.notification.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.notification.input.GetNotificationsInput;
import io.spiffy.common.api.notification.output.GetNotificationsOutput;
import io.spiffy.notification.service.AlertService;

@RequestMapping("/api/notification/getnotifications")
public class GetNotificationsAPI extends API<GetNotificationsInput, GetNotificationsOutput, AlertService> {

    @Inject
    public GetNotificationsAPI(final AlertService service) {
        super(GetNotificationsInput.class, service);
    }

    protected GetNotificationsOutput api(final GetNotificationsInput input) {
        return service.getByAccount(input.getAccountId());
    }
}
