package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.GetInput;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.input.GetMediaOutput;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.util.ObfuscateUtil;
import io.spiffy.stream.entity.PostEntity;
import io.spiffy.stream.service.PostService;

@RequestMapping("/api/stream/getpost")
public class GetPostAPI extends API<GetInput, GetPostOutput, PostService> {

    private final MediaClient mediaClient;
    private final UserClient userClient;

    @Inject
    public GetPostAPI(final PostService service, final MediaClient mediaClient, final UserClient userClient) {
        super(GetInput.class, service);
        this.mediaClient = mediaClient;
        this.userClient = userClient;
    }

    protected GetPostOutput api(final GetInput input) {
        final PostEntity e = service.get(input.getId());
        final Post post = transform(e);

        if (Boolean.FALSE.equals(e.getProcessed())) {
            return new GetPostOutput(post, GetPostOutput.Error.UNPROCESSED_MEDIA);
        }

        return new GetPostOutput(post);
    }

    public Post transform(final PostEntity e) {
        final GetMediaOutput media = mediaClient.getMedia(e.getMediaId());
        return new Post(ObfuscateUtil.obfuscate(e.getId()), e.getAccountId(), e.getTitle(), e.getDescription(), e.getPostedAt(),
                userClient.getAccount(e.getAccountId()).getUsername(), media.getContent());
    }
}