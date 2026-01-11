package com.myproject.employee_mang_backend.controller;

import com.myproject.employee_mang_backend.dto.EmployeeDto;
import com.myproject.employee_mang_backend.mapper.DepartmentMapper;
import com.myproject.employee_mang_backend.mapper.EmployeeMapper;
import com.myproject.employee_mang_backend.service.DepartmentService;
import com.myproject.employee_mang_backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees-ui")

public class EmployeeUiController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees",
                employeeMapper.toDtoList(employeeService.getAllEmployees()));
        return "employees";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("departments",
                departmentMapper.toDtoList(departmentService.getAllDepartments()));
        return "employee-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee",
                employeeMapper.toDto(employeeService.getEmployeeById(id)));
        model.addAttribute("departments",
                departmentMapper.toDtoList(departmentService.getAllDepartments()));
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") EmployeeDto dto,
            BindingResult result,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("departments",
                    departmentMapper.toDtoList(departmentService.getAllDepartments()));
            return "employee-form";
        }

        try {
            if (image != null && !image.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path path = Paths.get("uploads/" + fileName);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                dto.setImageUrl("/uploads/" + fileName);
            }

            if (dto.getId() == null) {
                employeeService.createEmployee(dto);
            } else {
                if (image == null || image.isEmpty()) {
                    EmployeeDto existing = employeeMapper.toDto(employeeService.getEmployeeById(dto.getId()));
                    dto.setImageUrl(existing.getImageUrl());
                }
                employeeService.updateEmployee(dto);
            }
            return "redirect:/employees-ui";

        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    e.getMessage() != null ? e.getMessage() : "Error saving employee: " + e.getClass().getSimpleName());
            model.addAttribute("departments",
                    departmentMapper.toDtoList(departmentService.getAllDepartments()));
            return "employee-form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees-ui";
    }
}
