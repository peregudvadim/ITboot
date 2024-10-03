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
public class EmployeeCreateDto {

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name can contain only Latin letters")
    private String lastName;

    @NotNull(message = "First name cannot be null")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name can contain only Latin letters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]*$", message = "Middle name can contain only Latin letters")
    private String middleName;

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Position cannot be null")
    @Pattern(regexp = "^[A-Za-z-]+$", message = "Position can contain only Latin letters and hyphens")
    private String position;
}
