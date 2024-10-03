package com.peregud.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void addProject_ShouldReturnProjectDto_WhenSavedSuccessfully() {
        ProjectCreateDto projectCreateDto = new ProjectCreateDto();
        ProjectEntity projectEntity = new ProjectEntity();
        ProjectEntity savedEntity = new ProjectEntity();
        savedEntity.setId(1L);
        ProjectDto projectDto = new ProjectDto();

        when(projectMapper.toEntity(projectCreateDto)).thenReturn(projectEntity);
        when(projectRepository.save(projectEntity)).thenReturn(savedEntity);
        when(projectMapper.toDto(savedEntity)).thenReturn(projectDto);

        ProjectDto result = projectService.addProject(projectCreateDto);

        assertNotNull(result);
        verify(projectMapper).toEntity(projectCreateDto);
        verify(projectRepository).save(projectEntity);
        verify(projectMapper).toDto(savedEntity);
    }

    @Test
    void getAllProjectsWithEmployees_ShouldReturnListOfProjectWithEmployeesDto() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setEmployeeEntities(Set.of(new EmployeeEntity()));
        List<ProjectEntity> projectEntityList = List.of(projectEntity);
        ProjectWithEmployeesDto projectWithEmployeesDto = new ProjectWithEmployeesDto();

        when(projectRepository.findAll()).thenReturn(projectEntityList);
        when(projectMapper.toEmployeeDto(projectEntity)).thenReturn(projectWithEmployeesDto);

        List<ProjectWithEmployeesDto> result = projectService.getAllProjectsWithEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projectRepository).findAll();
        verify(projectMapper).toEmployeeDto(projectEntity);
    }

    @Test
    void addEmployeeToProject_ShouldReturnUpdatedProjectDto_WhenEmployeeAdded() {
        Long employeeId = 1L;
        Long projectId = 1L;
        EmployeeEntity employeeEntity = new EmployeeEntity();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        ProjectEntity savedEntity = new ProjectEntity();
        savedEntity.setId(projectId);
        ProjectDto projectDto = new ProjectDto();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(projectRepository.save(projectEntity)).thenReturn(savedEntity);
        when(projectMapper.toDto(savedEntity)).thenReturn(projectDto);

        ProjectDto result = projectService.addEmployeeToProject(employeeId, projectId);

        assertNotNull(result);
        assertEquals(projectDto, result);
        verify(projectRepository).findById(projectId);
        verify(employeeRepository).findById(employeeId);
        verify(projectRepository).save(projectEntity);
        verify(projectMapper).toDto(savedEntity);
    }

    @Test
    void addEmployeeToProject_ShouldThrowProjectNotFoundException_WhenProjectDoesNotExist() {
        Long projectId = 1L;
        Long employeeId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.addEmployeeToProject(employeeId, projectId));
        verify(projectRepository).findById(projectId);
        verify(employeeRepository, never()).findById(anyLong());
        verify(projectRepository, never()).save(any(ProjectEntity.class));
    }

    @Test
    void addEmployeeToProject_ShouldThrowEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        Long employeeId = 1L;
        Long projectId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> projectService.addEmployeeToProject(employeeId, projectId));
        verify(projectRepository).findById(projectId);
        verify(employeeRepository).findById(employeeId);
        verify(projectRepository, never()).save(any(ProjectEntity.class));
    }

    @Test
    void findEmployee_ShouldReturnEmployeeEntity_WhenEmployeeExists() {
        Long employeeId = 1L;
        EmployeeEntity employeeEntity = new EmployeeEntity();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeEntity));

        EmployeeEntity result = projectService.findEmployee(employeeId);

        assertNotNull(result);
        assertEquals(employeeEntity, result);
        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void findEmployee_ShouldThrowEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        Long employeeId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> projectService.findEmployee(employeeId));
        verify(employeeRepository).findById(employeeId);
    }
}
