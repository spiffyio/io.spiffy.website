package io.spiffy.discussion.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.dto.Comment;
import io.spiffy.common.api.discussion.input.GetCommentsInput;
import io.spiffy.common.api.discussion.output.GetCommentsOutput;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/getcomments")
public class GetCommentsAPI extends API<GetCommentsInput, GetCommentsOutput, ThreadService> {

    @Inject
    public GetCommentsAPI(final ThreadService service) {
        super(GetCommentsInput.class, service);
    }

    protected GetCommentsOutput api(final GetCommentsInput input) {
        final List<CommentEntity> entities = service.getComments(input.getThread(), input.getFirst(), input.getMaxResults());

        final List<Comment> comments = new ArrayList<>();
        entities.forEach(e -> comments
                .add(new Comment(input.getThread(), e.getId(), e.getAccountId(), e.getPostedAt(), e.getComment())));

        return new GetCommentsOutput(comments);
    }
}