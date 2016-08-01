package io.spiffy.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_ACCOUNTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name", "archived_at" }),
        @UniqueConstraint(columnNames = { "email_address_id", "archived_at" }) })
public class AccountEntity extends HibernateEntity {

    public static final int MIN_USER_NAME_LENGTH = 3;
    public static final int MAX_USER_NAME_LENGTH = 25;

    @Setter
    @Column(name = "user_name", length = MAX_USER_NAME_LENGTH, nullable = false)
    private String userName;

    @Setter
    @Column(name = "email_address_id", nullable = false)
    private Long emailAddressId;

    @Setter
    @Transient
    private String emailAddress;
}
