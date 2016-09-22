package io.spiffy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicAccount {
    private Long id;
    private String username;
    private String iconUrl;
}
