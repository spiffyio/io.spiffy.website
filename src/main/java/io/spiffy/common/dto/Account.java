package io.spiffy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String username;
    private String email;
    private Boolean emailVerified;
    private String iconUrl;
    private String bannerUrl;

    public Account(final long id) {
        this.id = id;
    }

    public Account(final String username) {
        this.username = username;
    }
}
