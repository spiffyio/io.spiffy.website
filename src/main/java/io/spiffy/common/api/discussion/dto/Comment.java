package io.spiffy.common.api.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.spiffy.common.dto.PublicAccount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private ThreadDTO thread;
    private Long id;
    private PublicAccount account;
    private Date postedAt;
    private String comment;
}
