package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.valid.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractDto implements Serializable {

    @Null(groups = {Validation.OnCreate.class})
    @NotNull(groups = {Validation.OnUpdate.class})
    @Min(1)
    @Schema(description = "Identifier", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
}
