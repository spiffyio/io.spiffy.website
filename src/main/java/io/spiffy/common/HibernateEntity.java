package io.spiffy.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@MappedSuperclass
public abstract class HibernateEntity extends Entity {

    public static final int YES_NO_LENGTH = 1;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Setter
    @Column(name = "archived_at", nullable = true, updatable = true)
    private Date archivedAt;

    public HibernateEntity copy(final HibernateEntity entity, final Date archivedAt) {
        if (entity == null) {
            return null;
        }

        if (!getClass().equals(entity.getClass())) {
            return null;
        }

        final HibernateEntity copy = copy(entity);
        if (copy == null) {
            return copy;
        }

        copy.setArchivedAt(archivedAt);

        return null;
    }

    protected HibernateEntity copy(final HibernateEntity entity) {
        return null;
    }
}
