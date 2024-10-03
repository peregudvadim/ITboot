package com.peregud.controller;

import java.util.List;

import com.peregud.dto.ProjectCreateDto;
import com.peregud.dto.ProjectDto;
import com.peregud.dto.ProjectWithEmployeesDto;
import com.peregud.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.peregud.constants.Constants.ALL_URL;
import static com.peregud.constants.Constants.EMPLOYEE_PROJECT_URL;
import static com.peregud.constants.Constants.PROJECT_URL;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(PROJECT_URL)
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> addProject(@Valid @RequestBody ProjectCreateDto projectCreateDto) {
        return new ResponseEntity<>(projectService.addProject(projectCreateDto), HttpStatus.CREATED);
    }

    @GetMapping(ALL_URL)
    public ResponseEntity<List<ProjectWithEmployeesDto>> getAllProjectsWithEmployees() {
        List<ProjectWithEmployeesDto> projects = projectService.getAllProjectsWithEmployees();
        return ResponseEntity.ok(projects);
    }

    @PostMapping(EMPLOYEE_PROJECT_URL)
    public ResponseEntity<ProjectDto> addEmployeeToProject(@PathVariable Long employeeId,
        @PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.addEmployeeToProject(employeeId, projectId));
    }
}
