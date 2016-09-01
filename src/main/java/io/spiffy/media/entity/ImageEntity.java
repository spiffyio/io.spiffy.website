package io.spiffy.media.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEDIA_IMAGES", uniqueConstraints = @UniqueConstraint(columnNames = { "content", "archived_at" }))
public class ImageEntity extends HibernateEntity {

    @OneToOne
    @JoinColumn(name = "content", nullable = false)
    private ContentEntity content;

    @OneToOne
    @JoinColumn(name = "file", nullable = false)
    private FileEntity file;

    @OneToOne
    @JoinColumn(name = "medium", nullable = false)
    private FileEntity medium;

    @Setter
    @OneToOne
    @JoinColumn(name = "thumbnail")
    private FileEntity thumbnail;

    public ImageEntity(final ContentEntity content, final FileEntity file, final FileEntity medium,
            final FileEntity thumbnail) {
        this.content = content;
        this.file = file;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }
}
