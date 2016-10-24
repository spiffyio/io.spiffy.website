package io.spiffy.discussion.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.input.PostCommentInput;
import io.spiffy.common.api.output.PostOutput;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/postcomment")
public class PostCommentAPI extends API<PostCommentInput, PostOutput, ThreadService> {

    @Inject
    public PostCommentAPI(final ThreadService service) {
        super(PostCommentInput.class, service);
    }

    protected PostOutput api(final PostCommentInput input) {
        final CommentEntity entity = service.postComment(input.getThread(), input.getIdempotentId(), input.getAccountId(),
                input.getComment());
        final Long id = entity != null ? entity.getId() : null;
        return new PostOutput(id);
    }
}