package com.myproject.employee_mang_backend.service;

import com.myproject.employee_mang_backend.dto.DepartmentDto;
import com.myproject.employee_mang_backend.exception.EntityAlreadyExistsException;
import com.myproject.employee_mang_backend.mapper.DepartmentMapper;
import com.myproject.employee_mang_backend.model.Department;
import com.myproject.employee_mang_backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public Department createDepartment(DepartmentDto departmentDto) {
        Optional<Department> existDepartment = departmentRepository.findByCode(departmentDto.getCode());
        if (existDepartment.isPresent()) {
            throw new EntityAlreadyExistsException("This department code already exists!!");
        }
        return departmentRepository.save(departmentMapper.toEntity(departmentDto));
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
