package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    private String firstname;

    @NotNull(groups = {Validation.New.class})
    private String lastname;

    @NotNull(groups = {Validation.New.class})
    private LocalDate birthDate;

    @Null(groups = {Validation.New.class, Validation.Exists.class})
    private List<String> songNames = new ArrayList<>();
}
