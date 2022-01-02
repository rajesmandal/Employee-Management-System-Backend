package com.example.employeeManagementSystem.employee.web;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeRequestDto {

    @NotNull
    @Size(min = 3, max = 30, message = "Should be greater then 3 or less then 30")
    private String empName;
    private String role;
    private Double wagesPerHr;
}
