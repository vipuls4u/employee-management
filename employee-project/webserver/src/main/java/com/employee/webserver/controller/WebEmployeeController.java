package com.employee.webserver.controller;

import com.employee.webserver.model.Employee;
import com.employee.webserver.service.WebEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebEmployeeController {

    @Autowired
    protected WebEmployeeService employeeService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/employee")
    public Employee[] getAllEmployees (Model model){
       return employeeService.getAllEmployees();
    }

    @RequestMapping("/employees/id/{id}")
    public Employee getEmployeeByName (Model model, @PathVariable("id") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        return employee;
    }

    @RequestMapping("/employees")
    public Employee [] getAllEmployees (){
        Employee[] employee = employeeService.getAllEmployees();
        return employee;
    }

}
