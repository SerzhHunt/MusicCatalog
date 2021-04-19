package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "AlbumDto", description = "Data object for an album")
public class AlbumDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    @Size(min = 1, max = 255)
    @Schema(description = "Album name")
    private String name;

    @Null(groups = {Validation.New.class})
    @Schema(description = "Album duration", example = "58:17")
    private Duration duration;

    @Null(groups = {Validation.New.class, Validation.Exists.class})
    @Schema(description = "Album songs", example = "song name one, song name two")
    private List<String> songNames = new ArrayList<>();
}
