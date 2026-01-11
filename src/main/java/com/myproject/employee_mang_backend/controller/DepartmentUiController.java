package com.myproject.employee_mang_backend.controller;

import com.myproject.employee_mang_backend.dto.DepartmentDto;
import com.myproject.employee_mang_backend.mapper.DepartmentMapper;
import com.myproject.employee_mang_backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments-ui")
public class DepartmentUiController {
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @GetMapping
    public String departments(Model model) {
        model.addAttribute("department", new DepartmentDto());
        model.addAttribute("departments",
                departmentMapper.toDtoList(departmentService.getAllDepartments()));
        return "departments";
    }

    @PostMapping
    public String createDepartment(
            @Valid @ModelAttribute("department") DepartmentDto dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departments",
                    departmentMapper.toDtoList(departmentService.getAllDepartments()));
            return "departments";
        }

        departmentService.createDepartment(dto);
        return "redirect:/departments-ui";
    }
}
