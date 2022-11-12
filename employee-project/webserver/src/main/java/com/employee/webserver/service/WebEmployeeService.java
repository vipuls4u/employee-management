package com.employee.webserver.service;

import com.employee.webserver.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class WebEmployeeService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private String apiVersion = "/api/v1/";



    public Employee[] getAllEmployee(){
       // return restTemplate.getForObject("http://EMPLOYEE-SERVICE/api/v1/employees", Employee[].class);
        return restTemplate.getForObject("http://EMPLOYEE-ZUUL-SERVICE/producer/api/v1/employees", Employee[].class);

    }

    public Employee getEmployeeById(Long id){
        String baseUrl = getServiceInstance().getUri().toString();
        baseUrl = baseUrl + "/producer"+apiVersion+"employees/id/"+id;
        return restTemplate.getForObject(baseUrl , Employee.class);

    }

    private ServiceInstance getServiceInstance() {
        List<ServiceInstance> instances = discoveryClient.getInstances("EMPLOYEE-ZUUL-SERVICE");
        ServiceInstance serviceInstance = instances.get(0);
        return serviceInstance;
    }

    public ResponseEntity<Employee> getAllEmployees() throws IOException {
        String baseUrl = getServiceInstance().getUri().toString();
        baseUrl = baseUrl + "/producer"+apiVersion+"employees";
        //RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), Employee.class);
        return response;

    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
