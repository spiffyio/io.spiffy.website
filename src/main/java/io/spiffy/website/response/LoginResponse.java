package io.spiffy.website.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginResponse extends AjaxResponse {
    private String token;
}
