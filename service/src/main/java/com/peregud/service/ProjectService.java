package com.peregud.service;

import java.util.List;

import com.peregud.dto.ProjectCreateDto;
import com.peregud.dto.ProjectDto;
import com.peregud.dto.ProjectWithEmployeesDto;

public interface ProjectService {

    ProjectDto addProject(ProjectCreateDto projectCreateDto);

    List<ProjectWithEmployeesDto> getAllProjectsWithEmployees();

    ProjectDto addEmployeeToProject(Long employeeId, Long projectId);
}
