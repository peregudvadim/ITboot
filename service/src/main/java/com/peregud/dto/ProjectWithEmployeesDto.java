package com.peregud.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWithEmployeesDto {

    private Long id;
    private String projectName;
    private String projectDescription;
    private List<String> employees;
}
