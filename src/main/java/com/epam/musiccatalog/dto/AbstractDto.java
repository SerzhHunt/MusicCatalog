package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Null;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractDto implements Serializable {

    @Null(groups = {Validation.New.class, Validation.Exists.class})
    @Schema(description = "Identifier", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
}
