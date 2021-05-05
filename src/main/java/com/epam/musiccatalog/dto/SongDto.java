package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.valid.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "SongDto", description = "Data object for an song")
public class SongDto extends AbstractDto {

    @NotNull(groups = {Validation.OnCreate.class, Validation.OnUpdate.class})
    @Size(min = 1, max = 255)
    @Schema(description = "Song name")
    private String name;

    @NotNull(groups = {Validation.OnCreate.class})
    @Null(groups = {Validation.OnUpdate.class})
    @Schema(description = "Song duration", example = "3:45")
    private Duration duration;

    @NotNull(groups = {Validation.OnCreate.class, Validation.OnUpdate.class})
    @Schema(description = "Songwriter list", example = "author one, author two")
    private List<String> authorNames = new ArrayList<>();

    @Null(groups = {Validation.OnCreate.class, Validation.OnUpdate.class})
    @Schema(name = "Song album")
    private String albumName;
}
