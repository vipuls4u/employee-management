package com.management.employee.service.impl;

import com.management.employee.entity.Employee;
import com.management.employee.exception.ResourceNotFoundException;
import com.management.employee.repository.EmployeeRepository;
import com.management.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private BigDecimal salRange = new BigDecimal(50000);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployeeById(Long id) throws ResourceNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setId(Employee.getNextId());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = getEmployeeById(employeeId);
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setEmpName(employeeDetails.getEmpName());
        employee.setCountry(employeeDetails.getCountry());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDateOfJoining(employeeDetails.getDateOfJoining());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @Override
    public Employee deleteEmployee(Long employeeId) throws ResourceNotFoundException {
        Employee employee = getEmployeeById(employeeId);
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> eligibaleForBonus() {
        List<String> countries = Arrays.asList("IND");
        LocalDateTime startOfEveryYear = LocalDateTime.of(LocalDateTime.now().getYear(), 1, 1, 0, 0);
        LocalDateTime currentDateTime = LocalDateTime.now();
        List <Employee> employeeList =  employeeRepository.findByCountryInAndDateOfJoiningBetweenAndSalaryGreaterThan(
                countries,startOfEveryYear,currentDateTime,salRange);
        List<Employee> bonusApplied = employeeList.stream().map(this::applyBonus).collect(Collectors.toList());
      return employeeList;
    }

    /**
     *
     * @param employee
     * @return
     */
    private Employee applyBonus(Employee employee) {
       BigDecimal salary =  employee.getSalary();
        BigDecimal finalSalary = salary.add(salary.multiply(new BigDecimal(0.05)));
        employee.setSalary(finalSalary);
        return employee;
    }

}


