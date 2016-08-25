package io.spiffy.common.api.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private ThreadDTO thread;
    private Long id;
    private Long accountId;
    private Date postedAt;
    private String comment;
}
