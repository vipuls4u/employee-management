package com.management.employee.service;

import com.management.employee.entity.Employee;
import com.management.employee.exception.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(Long id) throws ResourceNotFoundException;

    List<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException;

    Employee deleteEmployee(Long employeeId) throws ResourceNotFoundException;

    List<Employee> eligibaleForBonus();
}
