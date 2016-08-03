package io.spiffy.source.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SOURCE_URLS")
public class UrlEntity extends HibernateEntity {

    public static final int MIN_DOMAIN_LENGTH = 1;
    public static final int MAX_DOMAIN_LENGTH = 256;

    public static final int MAX_ENTITY_ID_LENGTH = 256;

    public static final int MAX_ENTITY_OWNER_LENGTH = 256;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "domain", length = MAX_DOMAIN_LENGTH, nullable = false)
    private String domain;

    @Column(name = "entity_id", length = MAX_ENTITY_ID_LENGTH)
    private String entityId;

    @Column(name = "entity_owner", length = MAX_ENTITY_OWNER_LENGTH)
    private String entityOwner;
}
