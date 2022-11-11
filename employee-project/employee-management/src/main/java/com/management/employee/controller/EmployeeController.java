package com.management.employee.controller;

import com.management.employee.entity.Employee;
import com.management.employee.exception.ResourceNotFoundException;
import com.management.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    protected Logger logger = Logger.getLogger(EmployeeController.class
            .getName());

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees/name/{empName}")
    public Employee getEmpByName(@PathVariable("empName") String empName){
        return employeeRepository.findByEmpName(empName);
    }

    @GetMapping("/employees/id/{employeeId}")
    public Employee getEmpbyID(@PathVariable("employeeId") Long employeeId) throws  ResourceNotFoundException{
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee) {
        employee.setId(Employee.getNextId());
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = getEmpbyID(employeeId);
        employee.setEmailId(employeeDetails.getEmailId());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = getEmpbyID(employeeId);
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
