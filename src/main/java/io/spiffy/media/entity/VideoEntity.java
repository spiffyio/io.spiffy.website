package io.spiffy.media.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEDIA_VIDEOS", uniqueConstraints = @UniqueConstraint(columnNames = { "content", "archived_at" }))
public class VideoEntity extends HibernateEntity {

    @OneToOne
    @JoinColumn(name = "content", nullable = false)
    private ContentEntity content;

    @OneToOne
    @JoinColumn(name = "poster", nullable = false)
    private FileEntity poster;

    @OneToOne
    @JoinColumn(name = "mp4", nullable = false)
    private FileEntity mp4;

    @OneToOne
    @JoinColumn(name = "webm", nullable = false)
    private FileEntity webm;

    @OneToOne
    @JoinColumn(name = "gif")
    private FileEntity gif;

    @Setter
    @OneToOne
    @JoinColumn(name = "thumbnail")
    private FileEntity thumbnail;

    public VideoEntity(final ContentEntity content, final FileEntity poster, final FileEntity mp4, final FileEntity webm,
            final FileEntity gif, final FileEntity thumbnail) {
        this.content = content;
        this.poster = poster;
        this.mp4 = mp4;
        this.webm = webm;
        this.gif = gif;
        this.thumbnail = thumbnail;
    }
}
