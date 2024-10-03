package com.peregud.service.impl;

import java.util.Optional;

import com.peregud.dto.EmployeeCreateDto;
import com.peregud.dto.EmployeeDto;
import com.peregud.entity.EmployeeEntity;
import com.peregud.exception.EmployeeNotFoundException;
import com.peregud.mapper.EmployeeMapper;
import com.peregud.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void addEmployee_ShouldReturnEmployeeDto_WhenSavedSuccessfully() {
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        EmployeeEntity employeeEntity = new EmployeeEntity();
        EmployeeEntity savedEntity = new EmployeeEntity();
        savedEntity.setId(1L);
        EmployeeDto employeeDto = new EmployeeDto();

        when(employeeMapper.toEntity(employeeCreateDto)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(savedEntity);
        when(employeeMapper.toDto(savedEntity)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.addEmployee(employeeCreateDto);

        assertNotNull(result);
        verify(employeeMapper).toEntity(employeeCreateDto);
        verify(employeeRepository).save(employeeEntity);
        verify(employeeMapper).toDto(savedEntity);
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployeeDto_WhenEmployeeExists() {
        Long id = 1L;
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        EmployeeEntity existingEmployee = new EmployeeEntity();
        EmployeeEntity updatedEntity = new EmployeeEntity();
        updatedEntity.setId(id);
        EmployeeDto updatedDto = new EmployeeDto();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeMapper.toEntity(employeeCreateDto)).thenReturn(updatedEntity);
        when(employeeRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(employeeMapper.toDto(updatedEntity)).thenReturn(updatedDto);

        EmployeeDto result = employeeService.updateEmployee(id, employeeCreateDto);

        assertNotNull(result);
        assertEquals(updatedDto, result);
        verify(employeeRepository).findById(id);
        verify(employeeRepository).save(updatedEntity);
    }

    @Test
    void updateEmployee_ShouldThrowEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        Long id = 1L;
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(id, employeeCreateDto));
        verify(employeeRepository).findById(id);
        verify(employeeRepository, never()).save(any(EmployeeEntity.class));
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenEmployeeExists() {
        Long id = 1L;
        EmployeeEntity existingEmployee = new EmployeeEntity();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));

        employeeService.deleteEmployee(id);

        verify(employeeRepository).findById(id);
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void deleteEmployee_ShouldThrowEmployeeNotFoundException_WhenEmployeeDoesNotExist() {
        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(id));
        verify(employeeRepository).findById(id);
        verify(employeeRepository, never()).deleteById(id);
    }
}
