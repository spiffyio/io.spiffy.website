package io.spiffy.common;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.io.CharStreams;

import io.spiffy.common.config.AppConfig;
import io.spiffy.common.dto.Context;
import io.spiffy.common.util.CsrfUtil;
import io.spiffy.common.util.JsonUtil;

public abstract class API<Input, Output, Service> extends Controller {

    public static final boolean SUCCESS = true;
    public static final boolean FAILURE = !SUCCESS;

    public static final boolean VALID = true;
    public static final boolean INVALID = !VALID;

    private final Class<Input> inputClass;
    protected final Service service;

    protected API(final Class<Input> inputClass, final Service service) {
        this.inputClass = inputClass;
        this.service = service;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Output foo(final Context context) throws IOException {
        final String json = CharStreams.toString(context.getRequest().getReader());
        CsrfUtil.validateToken(json, AppConfig.getApiKey(), context.getHeader(Context.SPIFFY_API_CERTIFICATE));

        return api(JsonUtil.deserialize(inputClass, json));
    }

    protected abstract Output api(final Input input);
}
