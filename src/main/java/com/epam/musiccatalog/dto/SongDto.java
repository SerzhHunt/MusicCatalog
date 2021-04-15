package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class SongDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    @Null(groups = {Validation.Exists.class})
    private String name;

    @NotNull(groups = {Validation.New.class})
    private Duration duration;

    @NotNull(groups = {Validation.New.class})
    private Set<String> authorNames = new HashSet<>();

    @NotNull(groups = {Validation.New.class})
    private String albumName;
}
