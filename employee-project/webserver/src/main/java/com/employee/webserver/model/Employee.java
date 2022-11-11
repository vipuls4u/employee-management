package com.employee.webserver.model;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName("Account")
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;

    protected static Long nextId = 21L;

    protected Long id;

    protected String empName;

    protected String emailId;

    /**
     * This is a very simple, and non-scalable solution to generating unique
     * ids. Not recommended for a real application. Consider using the
     * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
     *
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

    public Employee(String empName, String emailId) {
        id = getNextId();
        this.empName = empName;
        this.emailId = emailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmpName() { return empName;    }

    public void setEmpName(String empName) { this.empName = empName;}

    public String getEmailId() { return emailId; }

    public void setEmailId(String emailId) {this.emailId = emailId;}



}
