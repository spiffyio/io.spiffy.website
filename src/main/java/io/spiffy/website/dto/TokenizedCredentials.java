package io.spiffy.website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.api.user.dto.Provider;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenizedCredentials {
    private Provider provider;
    private String providerId;
    private String email;
}
