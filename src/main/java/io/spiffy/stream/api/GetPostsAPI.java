package io.spiffy.stream.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.input.GetPostsInput;
import io.spiffy.common.api.stream.output.GetPostsOutput;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/getposts")
public class GetPostsAPI extends API<GetPostsInput, GetPostsOutput, PostService> {

    @Inject
    public GetPostsAPI(final PostService service) {
        super(GetPostsInput.class, service);
    }

    protected GetPostsOutput api(final GetPostsInput input) {
        final List<PostEntity> entities = service.get(input.getFirst(), input.getMaxResults());

        final List<Post> posts = new ArrayList<>();
        entities.forEach(entity -> posts.add(new Post(entity.getAccountId(), entity.getMediaId(), entity.getTitle(),
                entity.getDescription(), entity.getPostedAt())));

        return new GetPostsOutput(posts);
    }
}