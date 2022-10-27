package com.management.employee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_EMPLOYEE")
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;

    private static Long nextId = 21L;

    @Id
    private Long id;

    @Column(name = "Name")
    private String empName;

    @Column(name = "email_Id")
    private String emailId;

    @Column(name= "date_Of_Joining")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateOfJoining;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "country")
    private String country;



    /**
     * simple and non-scalable solution to generating unique
     * ids for Demos. Restricted and  not recommended for a real application.
     * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
     * @return The next available id.
     */
    public static Long getNextId() {
        synchronized (nextId) {
            return nextId++;
        }
    }

    /**
     * Default constructor for JPA only.
     */
    protected Employee() {

    }

    /**
     *
     * @param empName
     * @param emailId
     */

    public Employee(String empName, String emailId, BigDecimal salary, String country) {
        id = getNextId();
        this.empName = empName;
        this.emailId = emailId;
        this.dateOfJoining = LocalDateTime.now();
        this.salary = salary;
        this.country = country;
    }

    /**
     * Getter Setter refactor need to done using Lombok
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDateTime getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDateTime dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
