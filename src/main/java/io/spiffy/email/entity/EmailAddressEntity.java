package io.spiffy.email.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMAIL_ADDRESSES", uniqueConstraints = @UniqueConstraint(columnNames = { "address", "archived_at" }) )
public class EmailAddressEntity extends HibernateEntity {

    public static final int MIN_ADDRESS_LENGTH = 5;
    public static final int MAX_ADDRESS_LENGTH = 256;

    @Column(name = "address")
    private String address;
}