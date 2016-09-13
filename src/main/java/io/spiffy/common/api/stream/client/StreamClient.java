package io.spiffy.common.api.stream.client;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.stream.call.GetPostCall;
import io.spiffy.common.api.stream.call.GetPostsCall;
import io.spiffy.common.api.stream.call.PostActionCall;
import io.spiffy.common.api.stream.call.PostPostCall;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.input.GetPostsInput;
import io.spiffy.common.api.stream.input.PostActionInput;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.api.stream.output.GetPostsOutput;
import io.spiffy.common.api.stream.output.PostActionOutput;
import io.spiffy.common.api.stream.output.PostPostOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class StreamClient extends Client {

    private final GetPostCall getPostCall;
    private final GetPostsCall getPostsCall;
    private final PostActionCall postActionCall;
    private final PostPostCall postPostCall;

    public List<Post> getPosts(final int maxResults) {
        return getPosts(new GetPostsInput(null, null, maxResults, true));
    }

    public List<Post> getPosts(final Long first, final int maxResults, final boolean includeFirst) {
        return getPosts(new GetPostsInput(null, first, maxResults, includeFirst));
    }

    public List<Post> getPostsByAccount(final Long accountId, final int maxResults) {
        return getPosts(new GetPostsInput(accountId, null, maxResults, true));
    }

    public List<Post> getPosts(final Long accountId, final Long first, final int maxResults, final boolean includeFirst) {
        return getPosts(new GetPostsInput(accountId, first, maxResults, includeFirst));
    }

    private List<Post> getPosts(final GetPostsInput input) {
        final GetPostsOutput output = getPostsCall.call(input);
        return output.getPosts();
    }

    public GetPostOutput getPost(final long id) {
        return getPost(new GetInput(id));
    }

    public GetPostOutput getPost(final String name) {
        return getPost(new GetInput(name));
    }

    protected GetPostOutput getPost(final GetInput input) {
        final GetPostOutput output = getPostCall.call(input);
        return output;
    }

    public PostActionOutput postAction(final long postId, final long accountId, final PostActionInput.Action action) {
        final PostActionInput input = new PostActionInput(postId, accountId, action);
        final PostActionOutput output = postActionCall.call(input);
        return output;
    }

    public PostPostOutput postPost(final String idempotentId, final long accountId, final long mediaId,
            final String description) {
        final PostPostInput input = new PostPostInput(idempotentId, accountId, mediaId, description);
        final PostPostOutput output = postPostCall.call(input);
        return output;
    }
}