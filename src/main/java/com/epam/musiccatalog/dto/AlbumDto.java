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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(name = "AlbumDto", description = "Data object for an album")
public class AlbumDto extends AbstractDto {

    @NotNull(groups = {Validation.OnCreate.class,Validation.OnUpdate.class})
    @Size(min = 1, max = 255)
    @Schema(description = "Album name")
    private String name;

    private LocalDate createdDate;

    @Null(groups = {Validation.OnCreate.class,Validation.OnUpdate.class})
    @Schema(description = "Album duration", example = "58:17")
    private Duration duration;

    @Null(groups = {Validation.OnCreate.class, Validation.OnUpdate.class})
    @Schema(description = "Album songs", example = "song name one, song name two")
    private List<String> songNames = new ArrayList<>();
}
