package com.example.employee_manager.service;
import com.example.employee_manager.model.Employee;
import com.example.employee_manager.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllEmployees();
        }
        return employeeRepository
                .findByFullNameContainingIgnoreCase(keyword.trim());
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public boolean emailExists(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean emailExistsForOtherEmployee(
            String email, Long id) {
        return employeeRepository.existsByEmailAndIdNot(email, id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
