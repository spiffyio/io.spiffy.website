package io.spiffy.stream.api;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import io.spiffy.common.API;
import io.spiffy.common.api.input.GetInput;
import io.spiffy.common.api.media.client.MediaClient;
import io.spiffy.common.api.media.output.GetMediaOutput;
import io.spiffy.common.api.stream.dto.Post;
import io.spiffy.common.api.stream.output.GetPostOutput;
import io.spiffy.common.api.user.client.UserClient;
import io.spiffy.common.dto.Account;
import io.spiffy.common.dto.PublicAccount;
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
        final PostEntity e;
        if (input.getId() != null) {
            e = service.get(input.getId());
        } else {
            e = service.getByName(input.getName());
        }

        if (e == null) {
            return new GetPostOutput(GetPostOutput.Error.UNKNOWN_POST);
        }

        final Post post = transform(e);

        if (Boolean.FALSE.equals(e.getProcessed())) {
            return new GetPostOutput(post, GetPostOutput.Error.UNPROCESSED_MEDIA);
        }

        return new GetPostOutput(post);
    }

    public Post transform(final PostEntity e) {
        final GetMediaOutput media = mediaClient.getMedia(e.getMediaId());
        final Account account = userClient.getAccount(new Account(e.getAccountId()));
        return new Post(e.getName(), e.getDescription(), e.getPostedAt(),
                new PublicAccount(account.getId(), account.getUsername(), account.getIconUrl()), media.getContent());
    }
}