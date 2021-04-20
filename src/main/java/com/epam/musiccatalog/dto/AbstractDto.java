package com.epam.musiccatalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractDto implements Serializable {

    @Schema(description = "Identifier", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
}
