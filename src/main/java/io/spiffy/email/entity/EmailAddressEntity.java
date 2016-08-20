package io.spiffy.email.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import io.spiffy.common.HibernateEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "EMAIL_ADDRESSES", uniqueConstraints = @UniqueConstraint(columnNames = { "encrypted_address_id",
        "archived_at" }) )
public class EmailAddressEntity extends HibernateEntity {

    @Column(name = "encrypted_address_id", nullable = false)
    private Long encryptedAddressId;

    public EmailAddressEntity(final long encryptedAddressId) {
        this.encryptedAddressId = encryptedAddressId;
    }

    @Setter
    @Transient
    private String address;
}