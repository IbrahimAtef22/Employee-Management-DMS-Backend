package com.myproject.employee_mang_backend.mapper;

import com.myproject.employee_mang_backend.dto.DepartmentDto;
import com.myproject.employee_mang_backend.model.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper{

    // Entity → DTO
    DepartmentDto toDto(Department department);

    // DTO → Entity
    Department toEntity(DepartmentDto departmentDto);

    List<DepartmentDto> toDtoList(List<Department> departmentList);

}
