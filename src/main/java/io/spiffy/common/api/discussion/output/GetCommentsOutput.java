package io.spiffy.common.api.discussion.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.discussion.dto.Comment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsOutput {
    private List<Comment> comments;
}
