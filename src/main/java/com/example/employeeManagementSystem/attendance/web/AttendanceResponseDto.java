package com.example.employeeManagementSystem.attendance.web;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceResponseDto {
    private Integer attendanceId;
    private Integer workingHour;
    private Date date;

}
