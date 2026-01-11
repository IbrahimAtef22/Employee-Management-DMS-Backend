package com.myproject.employee_mang_backend.controller;

import com.myproject.employee_mang_backend.dto.EmployeeDto;
import com.myproject.employee_mang_backend.mapper.EmployeeMapper;
import com.myproject.employee_mang_backend.model.Employee;
import com.myproject.employee_mang_backend.service.EmployeeService;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody
                                                      EmployeeDto employeeDto) {
        employeeDto.setId(null);
        log.info("Creating a new employee with code ({})", employeeDto.getCode());
        Employee employee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeMapper.toDto(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
                                                      @Valid @RequestBody
                                                      EmployeeDto employeeDto) {
        employeeDto.setId(id);
        log.info("Updating an employee with id ({}), code ({})", employeeDto.getId(), employeeDto.getCode());
        Employee updatedEmployee = employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>(employeeMapper.toDto(updatedEmployee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeMapper.toDtoList(employeeList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeMapper.toDto(employee), HttpStatus.OK);
    }

}
