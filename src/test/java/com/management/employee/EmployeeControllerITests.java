package com.management.employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.management.employee.entity.Employee;
import com.management.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    /**
     *
     * Create Employee Test
     * @throws Exception
     */
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
        Employee employee = new Employee("vipul","emailId.test.com", new BigDecimal(50000), "IND");

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.empName",
                        is(employee.getEmpName())))
                .andExpect(jsonPath("$.country",
                        is(employee.getCountry())))
                .andExpect(jsonPath("$.emailId",
                        is(employee.getEmailId())));

    }

    /**
     * positive scenario - valid employee id
     * JUnit test for GET employee by id REST API
     * @throws Exception
     */
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        Employee employee = new Employee("vipul","emailId.test.com", new BigDecimal(50000), "IND");
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.empName", is(employee.getEmpName())))
                .andExpect(jsonPath("$.country", is(employee.getCountry())))
                .andExpect(jsonPath("$.emailId", is(employee.getEmailId())));

    }

    /**
     * negative scenario - valid employee id
     * JUnit test for GET employee by id REST API
     * @throws Exception
     */
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        // employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }


    /**
     * JUnit test for delete employee REST API
     * @throws Exception
     */
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        Employee savedEmployee = new Employee("vipul","emailId.test.com", new BigDecimal(50000), "IND");
        employeeRepository.save(savedEmployee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", savedEmployee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * Get All Employee Positive test
     * @throws Exception
     */
    @Test
    public void getEmployees_whenGetAllEmployee_thenReturn200() throws Exception{

        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(new Employee("vipul","emailId.test.com", new BigDecimal(50000), "IND"));
        listOfEmployees.add(new Employee("vipul","emailId.test.com", new BigDecimal(50000), "UK"));
        employeeRepository.saveAll(listOfEmployees);
        // given - precondition or setup
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));
    }


}