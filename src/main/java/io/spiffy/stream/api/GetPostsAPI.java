package io.spiffy.stream.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.input.GetPostsInput;
import io.spiffy.common.api.stream.output.GetPostsOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/getposts")
public class GetPostsAPI extends API<GetPostsInput, GetPostsOutput, PostService> {

    private final MediaClient mediaClient;
    private final UserClient userClient;

    @Inject
    public GetPostsAPI(final PostService service, final MediaClient mediaClient, final UserClient userClient) {
        super(GetPostsInput.class, service);
        this.mediaClient = mediaClient;
        this.userClient = userClient;
    }

    protected GetPostsOutput api(final GetPostsInput input) {
        final List<PostEntity> entities = service.get(input.getFirst(), input.getMaxResults());

        final List<Post> posts = new ArrayList<>();
        entities.forEach(e -> posts.add(transform(e)));

        return new GetPostsOutput(posts);
    }

    public Post transform(final PostEntity e) {
        final GetMediaOutput media = mediaClient.getMedia(e.getMediaId());
        return new Post(ObfuscateUtil.obfuscate(e.getId()), e.getAccountId(), e.getMediaId(), e.getTitle(), e.getDescription(),
                e.getPostedAt(), userClient.getAccount(e.getAccountId()), media.getUrl(), media.getTypes());
    }
}