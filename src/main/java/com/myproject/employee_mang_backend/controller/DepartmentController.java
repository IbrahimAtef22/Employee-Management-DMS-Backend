package com.myproject.employee_mang_backend.controller;

import com.myproject.employee_mang_backend.dto.DepartmentDto;
import com.myproject.employee_mang_backend.mapper.DepartmentMapper;
import com.myproject.employee_mang_backend.model.Department;
import com.myproject.employee_mang_backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody
                                                          DepartmentDto departmentDto) {
        log.info("Creating a new department with code ({})", departmentDto.getCode());
        Department department = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(departmentMapper.toDto(department), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<Department> departmentList = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentMapper.toDtoList(departmentList), HttpStatus.OK);
    }

}
