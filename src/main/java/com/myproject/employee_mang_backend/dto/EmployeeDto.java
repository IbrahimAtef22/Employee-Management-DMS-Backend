package com.myproject.employee_mang_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Employee code is required")
    private String code;

    @NotBlank(message = "Employee name is required")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String address;

    private String mobile;

    @NotNull(message = "Salary is required")
    private Double salary;

    @NotNull(message = "Department id is required")
    private Long departmentId;

    private String departmentName;

    private String imageUrl;
}
