package com.myproject.employee_mang_backend.service;

import com.myproject.employee_mang_backend.dto.EmployeeDto;
import com.myproject.employee_mang_backend.exception.EntityAlreadyExistsException;
import com.myproject.employee_mang_backend.exception.EntityNotFoundException;
import com.myproject.employee_mang_backend.mapper.EmployeeMapper;
import com.myproject.employee_mang_backend.model.Department;
import com.myproject.employee_mang_backend.model.Employee;
import com.myproject.employee_mang_backend.repository.DepartmentRepository;
import com.myproject.employee_mang_backend.repository.EmployeeRepository;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public Employee createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> existEmployee = employeeRepository.findByCode(employeeDto.getCode());
        if (existEmployee.isPresent()) {
            throw new EntityAlreadyExistsException("This employee code already exists!!");
        }
        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found !!"));
        employeeDto.setDepartmentId(department.getId());
        return employeeRepository.save(employeeMapper.toEntity(employeeDto));
    }

    public Employee updateEmployee(EmployeeDto employeeDto) {
        Employee existing = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        if (!existing.getCode().equals(employeeDto.getCode()) &&
                employeeRepository.findByCode(employeeDto.getCode()).isPresent()) {
            throw new EntityAlreadyExistsException("Employee code already exists");
        }
        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found !!"));
        employeeDto.setDepartmentId(department.getId());

        employeeMapper.updateEntityFromDto(employeeDto, existing);
        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        Employee existEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with this id doesn't exist"));
        employeeRepository.delete(existEmployee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with this id doesn't exist"));
    }
}
