package com.example.employeeManagementSystem.employee.web;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class EmployeeUpdateRequestDto {
    private Integer empId;
    @Size(min = 3, max = 30)
    private String empName;
    @Size(min = 3, max = 30)
    private String role;
    private Double wagesPerHr;
}
