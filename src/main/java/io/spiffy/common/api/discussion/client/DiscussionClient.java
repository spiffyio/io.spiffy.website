package io.spiffy.common.api.discussion.client;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.discussion.call.CreateThreadCall;
import io.spiffy.common.api.discussion.call.GetCommentsCall;
import io.spiffy.common.api.discussion.call.PostCommentCall;
import io.spiffy.common.api.discussion.dto.Comment;
import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.discussion.input.CreateThreadInput;
import io.spiffy.common.api.discussion.input.GetCommentsInput;
import io.spiffy.common.api.discussion.input.PostCommentInput;
import io.spiffy.common.api.discussion.output.CreateThreadOutput;
import io.spiffy.common.api.discussion.output.GetCommentsOutput;
import io.spiffy.common.dto.EntityType;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class DiscussionClient extends Client {

    private final CreateThreadCall createThreadCall;
    private final GetCommentsCall getCommentsCall;
    private final PostCommentCall postCommentCall;

    public boolean createThread(final String ... participants) {
        final CreateThreadInput input = null;
        final CreateThreadOutput output = createThreadCall.call(input);
        return output.getSuccess();
    }

    public List<Comment> getComments(final long id, final Long first, final int maxResults) {
        return getComments(new ThreadDTO(id), first, maxResults);
    }

    public List<Comment> getComments(final EntityType entityType, final String entityId, final Long first,
            final int maxResults) {
        return getComments(new ThreadDTO(entityType, entityId), first, maxResults);
    }

    private List<Comment> getComments(final ThreadDTO thread, final Long first, final int maxResults) {
        final GetCommentsInput input = new GetCommentsInput(thread, first, maxResults);
        final GetCommentsOutput output = getCommentsCall.call(input);
        return output.getComments();
    }

    public boolean postComment(final long id, final long accountId, final String idempotentId, final String comment) {
        return postComment(new ThreadDTO(id), accountId, idempotentId, comment);
    }

    public boolean postComment(final EntityType entityType, final String entityId, final long accountId,
            final String idempotentId, final String comment) {
        return postComment(new ThreadDTO(entityType, entityId), accountId, idempotentId, comment);
    }

    public boolean postComment(final ThreadDTO thread, final long accountId, final String idempotentId, final String comment) {
        final PostCommentInput input = new PostCommentInput(thread, accountId, idempotentId, comment);
        final PostOutput output = postCommentCall.call(input);
        return output.getId() != null;
    }
}