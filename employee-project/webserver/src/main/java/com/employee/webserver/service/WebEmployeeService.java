package com.employee.webserver.service;

import com.employee.webserver.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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
        return restTemplate.getForObject("http://EMPLOYEE-SERVICE/api/v1/employees", Employee[].class);

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


    public Employee[] getAllEmployees(){
        String baseUrl = getServiceInstance().getUri().toString();
        baseUrl = baseUrl + "/producer"+apiVersion+"employees";
        return restTemplate.getForObject(baseUrl , Employee[].class);

    }
}
