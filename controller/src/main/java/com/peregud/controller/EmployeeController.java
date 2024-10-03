package com.peregud.controller;

import com.peregud.dto.EmployeeCreateDto;
import com.peregud.dto.EmployeeDto;
import com.peregud.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.peregud.constants.Constants.EMPLOYEE_URL;
import static com.peregud.constants.Constants.PATH_ID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(EMPLOYEE_URL)
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeCreateDto employeeCreateDto) {
        return new ResponseEntity<>(employeeService.addEmployee(employeeCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
        @Valid @RequestBody EmployeeCreateDto employeeCreateDto) {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeCreateDto), HttpStatus.CREATED);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
