package com.peregud.service.impl;

import com.peregud.dto.EmployeeCreateDto;
import com.peregud.dto.EmployeeDto;
import com.peregud.entity.EmployeeEntity;
import com.peregud.exception.EmployeeNotFoundException;
import com.peregud.mapper.EmployeeMapper;
import com.peregud.repository.EmployeeRepository;
import com.peregud.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeCreateDto employeeCreateDto) {
        log.info("addEmployee started - employeeCreateDto: {}", employeeCreateDto);

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeCreateDto);
        EmployeeEntity saved = employeeRepository.save(employeeEntity);
        log.info("added employee with id: {}", saved.getId());

        return employeeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeCreateDto employeeCreateDto) {
        log.info("updateEmployee started - id: {}, employeeCreateDto: {}", id, employeeCreateDto);

        checkEmployee(id);

        EmployeeEntity toSave = employeeMapper.toEntity(employeeCreateDto);
        toSave.setId(id);

        EmployeeEntity saved = employeeRepository.save(toSave);
        log.info("updated employee with id: {}", saved.getId());

        return employeeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        log.info("deleteEmployee started - id: {}", id);

        checkEmployee(id);

        employeeRepository.deleteById(id);
        log.info("deleted employee with id: {}", id);
    }

    private void checkEmployee(Long id) {
        employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Can't find employee with id" + id));
    }
}
