package com.peregud.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.peregud.dto.ProjectCreateDto;
import com.peregud.dto.ProjectDto;
import com.peregud.dto.ProjectWithEmployeesDto;
import com.peregud.entity.EmployeeEntity;
import com.peregud.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(ProjectEntity projectEntity);

    ProjectEntity toEntity(ProjectCreateDto projectCreateDto);

    @Mapping(source = "employeeEntities", target = "employees", qualifiedByName = "mapEmployeesToFullNames")
    ProjectWithEmployeesDto toEmployeeDto(ProjectEntity projectEntity);

    @Named("mapEmployeesToFullNames")
    default List<String> mapEmployeesToFullNames(Set<EmployeeEntity> employeeEntities) {
        return employeeEntities.stream()
            .map(employee -> String.join(" ",
                employee.getLastName(),
                employee.getFirstName(),
                employee.getMiddleName() != null ? employee.getMiddleName() : ""))
            .collect(Collectors.toList());
    }
}
