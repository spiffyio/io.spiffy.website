package io.spiffy.common.api.discussion.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.discussion.dto.Comment;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetCommentsOutput extends APIOutput {
    private static final long serialVersionUID = -1113185708445991349L;
    private List<Comment> comments;
}
