package io.spiffy.common.api.stream.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.stream.dto.Post;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostOutput {
    private Post post;
}
