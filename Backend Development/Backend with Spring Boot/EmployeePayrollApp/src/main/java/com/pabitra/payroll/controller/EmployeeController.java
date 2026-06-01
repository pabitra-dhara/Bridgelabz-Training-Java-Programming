package com.pabitra.payroll.controller;

import com.pabitra.payroll.model.Employee;
import com.pabitra.payroll.service.EmployeeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public Employee addEmployee(
            @RequestBody Employee employee) {

        return service.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        service.delete(id);
        return "Employee Deleted";
    }
}
