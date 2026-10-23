package com.example.employee_manager.controller;
import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String listEmployees(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute(
                "employees", employeeService.searchEmployees(keyword));
        model.addAttribute("employee", new Employee());
        model.addAttribute("keyword", keyword);

        return "employees";
    }

    @PostMapping("/employees")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(required = false) String keyword) {

        if (employeeService.emailExists(employee.getEmail())) {
            result.rejectValue(
                    "email", "duplicate",
                    "This email is already registered");
        }

        if (result.hasErrors()) {
            model.addAttribute(
                    "employees", employeeService.searchEmployees(keyword));
            model.addAttribute("keyword", keyword);
            return "employees";
        }

        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute(
                "success", "Employee saved successfully!");

        return "redirect:/employees";
    }

    @GetMapping("/employees/{id}/edit")
    public String editEmployee(
            @PathVariable Long id, Model model) {

        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found: " + id));

        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/employees/{id}/update")
    public String updateEmployee(
            @PathVariable Long id,
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        Employee existing = employeeService.getEmployeeById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found: " + id));

        if (employeeService.emailExistsForOtherEmployee(
                employee.getEmail(), id)) {
            result.rejectValue(
                    "email", "duplicate",
                    "This email is already registered");
        }

        if (result.hasErrors()) {
            employee.setId(id);
            model.addAttribute("employee", employee);
            return "edit-employee";
        }

        existing.setFullName(employee.getFullName());
        existing.setEmail(employee.getEmail());
        existing.setPhone(employee.getPhone());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());

        employeeService.saveEmployee(existing);

        redirectAttributes.addFlashAttribute(
                "success", "Employee updated successfully!");

        return "redirect:/employees";
    }

    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute(
                "success", "Employee deleted successfully!");

        return "redirect:/employees";
    }
}
