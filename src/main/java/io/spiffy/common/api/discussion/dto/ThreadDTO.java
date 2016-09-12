package io.spiffy.common.api.discussion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.spiffy.common.dto.EntityType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDTO {
    private Long id;
    private EntityType entityType;
    private String entityId;

    public ThreadDTO(final long id) {
        this.id = id;
    }

    public ThreadDTO(final EntityType entityType, final String entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }
}
