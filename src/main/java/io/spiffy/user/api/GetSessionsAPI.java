package io.spiffy.user.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.user.dto.Session;
import io.spiffy.common.api.user.input.GetSessionsInput;
import io.spiffy.common.api.user.output.GetSessionsOutput;
import io.spiffy.common.util.UserAgentUtil;
import io.spiffy.user.entity.SessionEntity;
import io.spiffy.user.service.SessionService;

@RequestMapping("/api/user/getsessions")
public class GetSessionsAPI extends API<GetSessionsInput, GetSessionsOutput, SessionService> {

    @Inject
    public GetSessionsAPI(final SessionService service) {
        super(GetSessionsInput.class, service);
    }

    protected GetSessionsOutput api(final GetSessionsInput input) {
        final List<SessionEntity> entities = service.getByAccount(input.getAccountId());

        final List<Session> sessions = new ArrayList<>();
        entities.forEach(e -> sessions.add(new Session(e.getId(), UserAgentUtil.getOS(e.getLastUserAgent()),
                UserAgentUtil.getBrowser(e.getLastUserAgent()), e.getLastAccessedAt())));

        return new GetSessionsOutput(input.getAccountId(), sessions);
    }
}