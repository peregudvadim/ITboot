package com.peregud.service;

import com.peregud.dto.EmployeeCreateDto;
import com.peregud.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto addEmployee(EmployeeCreateDto employeeCreateDto);

    EmployeeDto updateEmployee(Long id, EmployeeCreateDto employeeCreateDto);

    void deleteEmployee(Long id);
}
