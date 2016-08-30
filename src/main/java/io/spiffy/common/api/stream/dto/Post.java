package io.spiffy.common.api.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.dto.PublicAccount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String postId;
    private String title;
    private String description;
    private Date postedAt;
    private PublicAccount account;
    private Content content;
}
