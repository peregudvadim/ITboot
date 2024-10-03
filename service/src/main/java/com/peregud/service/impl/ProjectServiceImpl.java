package com.peregud.service.impl;

import java.util.List;

import com.peregud.dto.ProjectCreateDto;
import com.peregud.dto.ProjectDto;
import com.peregud.dto.ProjectWithEmployeesDto;
import com.peregud.entity.EmployeeEntity;
import com.peregud.entity.ProjectEntity;
import com.peregud.exception.EmployeeNotFoundException;
import com.peregud.exception.ProjectNotFoundException;
import com.peregud.mapper.ProjectMapper;
import com.peregud.repository.EmployeeRepository;
import com.peregud.repository.ProjectRepository;
import com.peregud.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    @Override
    public ProjectDto addProject(ProjectCreateDto projectCreateDto) {
        log.info("addProject started - projectCreateDto: {}", projectCreateDto);

        ProjectEntity projectEntity = projectMapper.toEntity(projectCreateDto);
        ProjectEntity saved = projectRepository.save(projectEntity);

        log.info("added project with id: {}", saved.getId());

        return projectMapper.toDto(saved);
    }

    @Override
    public List<ProjectWithEmployeesDto> getAllProjectsWithEmployees() {
        log.info("getAllProjectsWithEmployees started");

        List<ProjectEntity> projectEntityList = projectRepository.findAll();

        return projectEntityList.stream()
            .map(projectMapper::toEmployeeDto)
            .toList();
    }

    @Override
    public ProjectDto addEmployeeToProject(Long employeeId, Long projectId) {
        log.info("addEmployeeToProject started - employeeId: {}, projectId: {}", employeeId, projectId);

        ProjectEntity projectEntity = projectRepository.findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException("Can't find project with id" + projectId));;

        EmployeeEntity employeeEntity = findEmployee(employeeId);
        projectEntity.getEmployeeEntities().add(employeeEntity);

        ProjectEntity saved = projectRepository.save(projectEntity);

        log.info("added employee to project with id: {}", saved.getId());

        return projectMapper.toDto(saved);
    }

    protected EmployeeEntity findEmployee(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Can't find employee with id" + id));
    }
}
