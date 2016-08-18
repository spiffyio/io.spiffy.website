package io.spiffy.website.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import io.spiffy.common.api.stream.dto.Post;

@Data
@EqualsAndHashCode(callSuper = false)
public class PostsResponse extends AjaxResponse {
    private final List<Post> posts;
    private final String next;
}
