package io.spiffy.common.api.notification.output;

import lombok.*;

import java.util.List;

import io.spiffy.common.api.notification.dto.Notification;
import io.spiffy.common.api.output.APIOutput;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetNotificationsOutput extends APIOutput {
    private static final long serialVersionUID = -6930675489578570616L;
    private List<Notification> notifications;
}
