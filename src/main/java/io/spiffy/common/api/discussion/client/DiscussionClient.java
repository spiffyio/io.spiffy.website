package io.spiffy.common.api.discussion.client;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.discussion.call.*;
import io.spiffy.common.api.discussion.dto.Comment;
import io.spiffy.common.api.discussion.dto.ThreadDTO;
import io.spiffy.common.api.discussion.input.*;
import io.spiffy.common.api.discussion.output.*;
import io.spiffy.common.dto.EntityType;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DiscussionClient extends Client {

    private final CreateThreadCall createThreadCall;
    private final GetCommentsCall getCommentsCall;
    private final GetMessagesCall getMessagesCall;
    private final GetThreadsCall getThreadsCall;
    private final PostCommentCall postCommentCall;
    private final PostMessageCall postMessageCall;

    public CreateThreadOutput createThread(final long accountId, final Set<String> participants) {
        final CreateThreadInput input = new CreateThreadInput(new ThreadDTO(EntityType.MESSAGE, null), accountId, participants);
        final CreateThreadOutput output = createThreadCall.call(input);
        return output;
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

    public GetMessagesOutput getMessages(final long accountId, final Set<String> participants, final String after) {
        final GetMessagesInput input = new GetMessagesInput(new ThreadDTO(EntityType.MESSAGE, null), accountId, participants,
                after);
        final GetMessagesOutput output = getMessagesCall.call(input);
        return output;
    }

    public GetThreadsOutput getThreads(final long accountId) {
        final GetThreadsInput input = new GetThreadsInput(accountId);
        final GetThreadsOutput output = getThreadsCall.call(input);
        return output;
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

    public PostMessageOutput postMessage(final long accountId, final Set<String> participants, final String idempotentId,
            final String comment) {
        final PostMessageInput input = new PostMessageInput(new ThreadDTO(EntityType.MESSAGE, null), accountId, participants,
                idempotentId, comment);
        final PostMessageOutput output = postMessageCall.call(input);
        return output;
    }
}