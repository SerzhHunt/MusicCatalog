package com.epam.musiccatalog.dto;

import com.epam.musiccatalog.valid.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"songNames"})
@Schema(name = "AuthorDto", description = "Data object for an author")
public class AuthorDto extends AbstractDto {

    @NotBlank(groups = {Validation.OnCreate.class})
    @Size(min = 2, max = 100)
    @Schema(description = "firstname of author")
    private String firstname;

    @NotBlank(groups = {Validation.OnCreate.class})
    @Size(min = 1, max = 100)
    @Schema(description = "lastname of author")
    private String lastname;

    @NotNull(groups = {Validation.OnCreate.class})
    @Null(groups = {Validation.OnUpdate.class})
    @Schema(description = "author's date of birth")
    private LocalDate birthDate;

    @Schema(description = "list of songs of the author", example = "song one, song two")
    @Null(groups = {Validation.OnCreate.class, Validation.OnUpdate.class})
    private List<String> songNames;
}
