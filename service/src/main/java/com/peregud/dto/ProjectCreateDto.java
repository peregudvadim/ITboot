package com.peregud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateDto {

    @NotNull(message = "Project name cannot be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Project name can contain only Latin letters")
    private String projectName;

    @Pattern(regexp = "^[A-Za-z ]*$", message = "Project description can contain only Latin letters")
    private String projectDescription;
}
