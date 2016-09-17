package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ThreadsResponse extends AjaxResponse {
    private final List<Thread> threads;

    @Data
    public static class Thread {
        private final String id;
        private final String icon;
        private final String time;
        private final String preview;
    }
}
