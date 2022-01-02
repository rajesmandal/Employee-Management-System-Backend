package com.example.employeeManagementSystem.attendance.web;

import com.example.employeeManagementSystem.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping(value = "/mark")
    public String attendance(@RequestBody AttendanceRequestDto attendanceRequestDto)
    {
        return attendanceService.markAttendance(attendanceRequestDto);
    }
    @PutMapping(value = "/update")
    public String updateAttendance(@RequestBody AttendanceUpdateRequestDto attendanceUpdateRequestDto){
        return attendanceService.updateAttendance(attendanceUpdateRequestDto);
    }

//    @GetMapping(value = "/employee/{id}")
//    public List<AttendanceResponseDto> getAttendance(@PathVariable Integer id){
//        return attendanceService.getAttendance(id);
//    }

    @GetMapping(value = "/employee")
    public List<AttendanceResponseDto> getAttendanceByMonth(Integer id, Month month, Integer year)
    {
        return attendanceService.getAttendanceByMonth(id, month, year);
    }
}
