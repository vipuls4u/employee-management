package com.management.employee.repository;

import com.management.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findByEmpName(String empName);

    public List<Employee> findBySalaryBetween(BigDecimal start, BigDecimal end);

    List<Employee> findByCountryInAndDateOfJoiningBetweenAndSalaryGreaterThan( List<String> country ,LocalDateTime of, LocalDateTime now, BigDecimal range);
}
