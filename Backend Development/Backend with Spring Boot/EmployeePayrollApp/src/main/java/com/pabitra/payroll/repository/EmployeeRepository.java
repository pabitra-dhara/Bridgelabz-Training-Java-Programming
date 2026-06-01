package com.pabitra.payroll.repository;

import com.pabitra.payroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee,Long> {
}