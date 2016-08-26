package io.spiffy.common.api.stream.client;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.inject.Inject;

import io.spiffy.common.Client;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.PostOutput;
import io.spiffy.common.api.stream.call.GetPostCall;
import io.spiffy.common.api.stream.call.GetPostsCall;
import io.spiffy.common.api.stream.call.PostPostCall;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.input.GetPostsInput;
import io.spiffy.common.api.stream.input.PostPostInput;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.api.stream.output.GetPostsOutput;

@RequiredArgsConstructor(onConstructor = @__(@Inject) )
public class StreamClient extends Client {

    private final GetPostCall getPostCall;
    private final GetPostsCall getPostsCall;
    private final PostPostCall postCallCall;

    public List<Post> getPosts(final int maxResults) {
        return getPosts(new GetPostsInput(null, null, maxResults));
    }

    public List<Post> getPosts(final Long first, final int maxResults) {
        return getPosts(new GetPostsInput(null, first, maxResults));
    }

    public List<Post> getPostsByAccount(final Long accountId, final int maxResults) {
        return getPosts(new GetPostsInput(accountId, null, maxResults));
    }

    public List<Post> getPosts(final Long accountId, final Long first, final int maxResults) {
        return getPosts(new GetPostsInput(accountId, first, maxResults));
    }

    private List<Post> getPosts(final GetPostsInput input) {
        final GetPostsOutput output = getPostsCall.call(input);
        return output.getPosts();
    }

    public Post getPost(final long id) {
        final GetInput input = new GetInput(id);
        final GetPostOutput output = getPostCall.call(input);
        return output.getPost();
    }

    public long postPost(final String idempotentId, final long accountId, final long mediaId, final String title,
            final String description) {
        final PostPostInput input = new PostPostInput(idempotentId, accountId, mediaId, title, description);
        final PostOutput output = postCallCall.call(input);
        return output.getId();
    }
}