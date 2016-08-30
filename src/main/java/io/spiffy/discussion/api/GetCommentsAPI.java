package io.spiffy.discussion.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.discussion.dto.Comment;
import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.discussion.input.GetCommentsInput;
import io.spiffy.common.api.discussion.output.GetCommentsOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.PublicAccount;
import io.spiffy.discussion.entity.CommentEntity;
import io.spiffy.discussion.service.ThreadService;

@RequestMapping("/api/discussion/getcomments")
public class GetCommentsAPI extends API<GetCommentsInput, GetCommentsOutput, ThreadService> {

    private final UserClient userClient;

    @Inject
    public GetCommentsAPI(final ThreadService service, final UserClient userClient) {
        super(GetCommentsInput.class, service);
        this.userClient = userClient;
    }

    protected GetCommentsOutput api(final GetCommentsInput input) {
        final List<CommentEntity> entities = service.getComments(input.getThread(), input.getFirst(), input.getMaxResults());

        final List<Comment> comments = new ArrayList<>();
        entities.forEach(e -> comments.add(transform(input.getThread(), e)));

        return new GetCommentsOutput(comments);
    }

    private Comment transform(final ThreadDTO thread, final CommentEntity e) {
        final Account account = userClient.getAccount(e.getAccountId());
        return new Comment(thread, e.getId(), new PublicAccount(account.getId(), account.getUsername()), e.getPostedAt(),
                e.getComment());
    }
}