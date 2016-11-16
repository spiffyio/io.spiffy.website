package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_ACCOUNTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name", "archived_at" }),
        @UniqueConstraint(columnNames = { "email_verification_token_id", "archived_at" }) })
public class AccountEntity extends HibernateEntity {

    public static final int MIN_USER_NAME_LENGTH = 3;
    public static final int MAX_USER_NAME_LENGTH = 25;

    @Setter
    @Column(name = "user_name", length = MAX_USER_NAME_LENGTH, nullable = false)
    private String userName;

    @Setter
    @Column(name = "email_address_id")
    private Long emailAddressId;

    @Setter
    @Type(type = "yes_no")
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Setter
    @Column(name = "email_verification_token_id")
    private Long emailVerificationTokenId;

    @Setter
    @Column(name = "icon_id")
    private Long iconId;

    @Setter
    @Column(name = "banner_id")
    private Long bannerId;

    @Setter
    @Transient
    private String emailAddress;

    @Setter
    @Transient
    private String emailVerificationToken;
}
