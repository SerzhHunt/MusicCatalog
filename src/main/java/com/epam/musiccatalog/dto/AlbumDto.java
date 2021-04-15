package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlbumDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    private String name;

    @Null(groups = {Validation.New.class})
    private Duration duration;

    @Null(groups = {Validation.New.class, Validation.Exists.class})
    private List<String> songNames = new ArrayList<>();
}
