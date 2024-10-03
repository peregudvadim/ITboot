package com.peregud.mapper;

import com.peregud.dto.EmployeeCreateDto;
import com.peregud.dto.EmployeeDto;
import com.peregud.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(EmployeeEntity employeeEntity);

    EmployeeEntity toEntity(EmployeeCreateDto employeeCreateDto);
}
