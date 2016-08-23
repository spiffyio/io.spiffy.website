package io.spiffy.common.api.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import io.spiffy.common.api.media.dto.MediaType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String postId;
    private Long accountId;
    private Long mediaId;
    private String title;
    private String description;
    private Date postedAt;
    private String username;
    private String url;
    private List<MediaType> types;
}
