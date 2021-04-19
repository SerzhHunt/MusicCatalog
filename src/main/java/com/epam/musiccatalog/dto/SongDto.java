package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "SongDto", description = "Data object for an song")
public class SongDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    @Null(groups = {Validation.Exists.class})
    @Size(min = 1, max = 255)
    @Schema(description = "Song name")
    private String name;

    @NotNull(groups = {Validation.New.class})
    @Schema(description = "Song duration", example = "3:45")
    private Duration duration;

    @NotNull(groups = {Validation.New.class})
    @Schema(description = "songwriter list", example = "author one, author two")
    private Set<String> authorNames = new HashSet<>();

    @NotNull(groups = {Validation.New.class})
    @Schema(name = "Song album")
    private String albumName;
}
