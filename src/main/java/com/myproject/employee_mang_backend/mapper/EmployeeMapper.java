package com.myproject.employee_mang_backend.mapper;

import com.myproject.employee_mang_backend.dto.EmployeeDto;
import com.myproject.employee_mang_backend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Entity → DTO
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    EmployeeDto toDto(Employee employee);

    // DTO → Entity
    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employeeList);

    void updateEntityFromDto(EmployeeDto dto, @MappingTarget Employee entity);

}
