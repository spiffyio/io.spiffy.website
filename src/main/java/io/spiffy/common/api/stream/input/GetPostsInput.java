package io.spiffy.common.api.stream.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostsInput {
    private Long first;
    private Integer maxResults;
}