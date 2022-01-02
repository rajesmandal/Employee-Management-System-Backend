package com.example.employeeManagementSystem.attendance.service;

import com.example.employeeManagementSystem.attendance.data.AttendanceEntity;
import com.example.employeeManagementSystem.attendance.data.AttendanceRepository;
import com.example.employeeManagementSystem.attendance.web.AttendanceRequestDto;
import com.example.employeeManagementSystem.attendance.web.AttendanceResponseDto;
import com.example.employeeManagementSystem.attendance.web.AttendanceUpdateRequestDto;
import com.example.employeeManagementSystem.common.util.Utility;
import com.example.employeeManagementSystem.employee.service.EmployeeService;
import com.example.employeeManagementSystem.salary.data.SalaryEntity;
import com.example.employeeManagementSystem.salary.service.SalaryService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeService employeeService;
    private final SalaryService salaryService;



    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeService employeeService, SalaryService salaryService) {
        this.attendanceRepository = attendanceRepository;
        this.employeeService = employeeService;
        this.salaryService = salaryService;
    }

    public String markAttendance(AttendanceRequestDto attendanceRequestDto) {
        Date today = Calendar.getInstance().getTime();
        if(!attendanceRequestDto.getDate().before(today)){
            return "Invalid Date";
        }
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        SalaryEntity salaryEntity = salaryService.getSalaryByEmployeeMonthYear(attendanceRequestDto.getEmpId(),
                Utility.getMonth(attendanceRequestDto.getDate()), Utility.getYear(attendanceRequestDto.getDate()));
        attendanceEntity.setSalaryEntity(salaryEntity);
        attendanceEntity.setDate(attendanceRequestDto.getDate());
        attendanceEntity.setWorkingHr(attendanceRequestDto.getWorkingHour());
        attendanceRepository.save(attendanceEntity);
        return "Employee Attendance Successfully";
    }
    public String updateAttendance(AttendanceUpdateRequestDto attendanceUpdateRequestDto){
        Date today = Calendar.getInstance().getTime();
        if(attendanceUpdateRequestDto.getDate().after(today)){
            return "Invalid Date";
        }
        AttendanceEntity attendanceEntity = getAttendanceById(attendanceUpdateRequestDto.getAttendanceId());
        attendanceEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYear(attendanceUpdateRequestDto.getEmpId(),
                Utility.getMonth(attendanceUpdateRequestDto.getDate()), Utility.getYear(attendanceUpdateRequestDto.getDate())));
        attendanceEntity.setDate(Utility.checkUpdate(attendanceUpdateRequestDto.getDate(),attendanceEntity.getDate()));
        attendanceEntity.setWorkingHr(Utility.checkUpdate(attendanceUpdateRequestDto.getWorkingHour(),attendanceEntity.getWorkingHr()));
        attendanceRepository.save(attendanceEntity);
        return "Employee Attendance Updated Successfully";
    }
    public AttendanceEntity getAttendanceById(Integer id){
        return attendanceRepository.findById(id).get();
    }

//    public List<AttendanceResponseDto> getAttendance (Integer empId)
//    {
//        SalaryEntity salaryEntity = salaryService.getEmployeeById(empId);
//        List<AttendanceEntity> attendanceEntitySet = salaryEntity.getAttendanceEntityList();
//        List<AttendanceResponseDto> attendanceResponseDtoList = new ArrayList<>();
//        attendanceEntitySet.forEach(attendanceEntity -> {
//            attendanceResponseDtoList.add(convertEntityToDto(attendanceEntity));
//        });
//
//        return attendanceResponseDtoList;
//    }

    private AttendanceResponseDto convertEntityToDto(AttendanceEntity attendanceEntity) {
        AttendanceResponseDto attendanceResponseDto = new AttendanceResponseDto();
        attendanceResponseDto.setAttendanceId(attendanceEntity.getAttendanceId());
        attendanceResponseDto.setDate(attendanceEntity.getDate());
        attendanceResponseDto.setWorkingHour(attendanceEntity.getWorkingHr());
        return attendanceResponseDto;

    }

    public Integer getTotalWorkingHourBySalaryId(SalaryEntity salaryEntity){
        return attendanceRepository.getTotalWorkingHourBySalaryId(salaryEntity);
    }

    public List<AttendanceResponseDto> getAttendanceByMonth(Integer id, Month month, Integer year) {

        SalaryEntity salaryEntity = salaryService.getSalaryByEmployeeMonthYear(id, month, year);
        List<AttendanceEntity> attendanceEntityList = salaryEntity.getAttendanceEntityList();
        List<AttendanceResponseDto> attendanceResponseDtoList = new ArrayList<>();
        attendanceEntityList.forEach(attendanceEntity -> {
            attendanceResponseDtoList.add(convertEntityToDto(attendanceEntity));
        });

        return attendanceResponseDtoList;
    }
}
