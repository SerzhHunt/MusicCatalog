package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.transfer.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"songNames"})
@Schema(name = "AuthorDto", description = "Data object for an author")
public class AuthorDto extends AbstractDto {

    @NotNull(groups = {Validation.New.class})
    @Size(min = 1, max = 255)
    @Schema(description = "firstname of author")
    private String firstname;

    @NotNull(groups = {Validation.New.class})
    @Size(min = 1, max = 255)
    @Schema(description = "lastname of author")
    private String lastname;

    @NotNull(groups = {Validation.New.class})
    @Null(groups = {Validation.Exists.class})
    @Schema(description = "author's date of birth")
    private LocalDate birthDate;

    @Null(groups = {Validation.New.class, Validation.Exists.class})
    @Schema(description = "list of songs of the author", example = "song one, song two")
    private List<String> songNames = new ArrayList<>();
}
