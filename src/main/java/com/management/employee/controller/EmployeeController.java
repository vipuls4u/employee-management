package com.management.employee.controller;

import com.management.employee.entity.Employee;
import com.management.employee.exception.ResourceNotFoundException;
import com.management.employee.repository.EmployeeRepository;
import com.management.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Rest Controller
 *
 */

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    protected Logger logger = Logger.getLogger(EmployeeController.class
            .getName());

    @Autowired
    EmployeeService employeeService;

    /**
     *
     * @param employeeId
     * @return
     * @throws ResourceNotFoundException
     */
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeByID(@PathVariable("employeeId") Long employeeId) throws  ResourceNotFoundException{
        Employee employee = employeeService.getEmployeeById(employeeId);
        return employee;

    }

    /**
     *
     * @return
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return employees;
    }

    /**
     *
     * @param newEmployee
     * @return
     */
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Validated @RequestBody Employee newEmployee) {
        Employee employee = employeeService.createEmployee(newEmployee);
        return employee;
    }

    /**
     *
     * @param employeeId
     * @param employeeDetails
     * @return
     * @throws ResourceNotFoundException
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     *
     * @param employeeId
     * @return
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee deleteddEmployee = employeeService.deleteEmployee(employeeId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * For Demo Only put this as part of Controller,
     * it should be through cron scheduler which will run once a year based on cron expression.
     * or There is need to keep flag in DB for bonus employee to avoid multiple run for same employee.
     * @return
     */
    @GetMapping("/employees/bonus")
    public List<Employee> eligibaleForBonus(){
        List<Employee> bonusAppliedToEmps = employeeService.eligibaleForBonus();
        return bonusAppliedToEmps;
    }

}
