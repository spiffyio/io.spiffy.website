package io.spiffy.common.api.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import io.spiffy.common.api.media.dto.Content;
import io.spiffy.common.api.media.dto.ContentType;
import io.spiffy.common.dto.PublicAccount;
import io.spiffy.common.util.DurationUtil;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String postId;
    private String title;
    private String description;
    private Date postedAt;
    private PublicAccount account;
    private Content content;

    public String getDuration() {
        return DurationUtil.pretty(postedAt);
    }

    public void setDuration(final String duration) {
    }

    public static Post ad() {
        final Content content = new Content();
        content.setType(ContentType.AD);

        final Post post = new Post();
        post.setPostId("ad");
        post.setContent(content);

        return post;
    }
}
