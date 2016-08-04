package io.spiffy.common.api.stream.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.stream.dto.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsOutput {
    private List<Post> posts;
}
