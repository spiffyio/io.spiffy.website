package io.spiffy.common.api.notification.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import io.spiffy.common.api.notification.dto.Notification;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNotificationsOutput {
    private List<Notification> notifications;
}
