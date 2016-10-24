package io.spiffy.common.api.stream.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.output.APIOutput;
import io.spiffy.common.api.stream.dto.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetPostsOutput extends APIOutput {
    private static final long serialVersionUID = -5088771959612207357L;
    private List<Post> posts;
}
