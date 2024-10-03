package com.peregud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String position;
}
