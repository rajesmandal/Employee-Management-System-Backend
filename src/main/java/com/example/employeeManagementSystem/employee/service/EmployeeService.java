package com.example.employeeManagementSystem.employee.service;

import com.example.employeeManagementSystem.common.util.Utility;
import com.example.employeeManagementSystem.employee.data.EmployeeEntity;
import com.example.employeeManagementSystem.employee.data.EmployeeRepository;
import com.example.employeeManagementSystem.employee.web.EmployeeRequestDto;
import com.example.employeeManagementSystem.employee.web.EmployeeResponseDto;
import com.example.employeeManagementSystem.employee.web.EmployeeUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    public String createEmployee(EmployeeRequestDto employeeRequestDto){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        if (Utility.checkCreate(employeeRequestDto.getEmpName(),employeeRequestDto.getRole(),employeeRequestDto.getWagesPerHr())){
            employeeEntity.setEmpName(employeeRequestDto.getEmpName());
            employeeEntity.setRole(employeeRequestDto.getRole());
            employeeEntity.setWagesPerHr(employeeRequestDto.getWagesPerHr());
            employeeRepository.save(employeeEntity);
            return "Employee Created Successfully";
        }
        return "Please enter mandatory fields";
    }

    public List<EmployeeResponseDto> getAllEmployee(){
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();
        employeeEntityList.forEach(employeeEntity -> {
            employeeResponseDtoList.add(convertEntityToDto(employeeEntity));
        });
        return employeeResponseDtoList;
    }

    public List<EmployeeResponseDto> getEmpByName (String empName){
        List<EmployeeEntity> employeeEntityList = employeeRepository.findByName(empName);
        List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();

        employeeEntityList.forEach(employeeEntity -> {
            employeeResponseDtoList.add(convertEntityToDto(employeeEntity));
        });
        return employeeResponseDtoList;
    }

    public String updateEmployee(EmployeeUpdateRequestDto employeeUpdateRequestDto)
    {
        EmployeeEntity employeeEntity = getEmployeeById(employeeUpdateRequestDto.getEmpId());
        employeeEntity.setEmpName(Utility.checkUpdate(employeeEntity.getEmpName(),employeeUpdateRequestDto.getEmpName()));
        employeeEntity.setRole(Utility.checkUpdate(employeeEntity.getRole(),employeeUpdateRequestDto.getRole()));
        employeeEntity.setWagesPerHr(Utility.checkUpdate(employeeEntity.getWagesPerHr(),employeeUpdateRequestDto.getWagesPerHr()));
        employeeRepository.save(employeeEntity);
        return "Successfully Updated";
    }



    public EmployeeEntity getEmployeeById(Integer id){
        return employeeRepository.findById(id).get();
    }

    public EmployeeResponseDto getEmployee(Integer id)
    {
        return convertEntityToDto(getEmployeeById(id));
    }


    private EmployeeResponseDto convertEntityToDto(EmployeeEntity employeeEntity)
    {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setEmpId(employeeEntity.getEmpId());
        employeeResponseDto.setEmpName(employeeEntity.getEmpName());
        employeeResponseDto.setRole(employeeEntity.getRole());
        employeeResponseDto.setWagesPerHr(employeeEntity.getWagesPerHr());
        return employeeResponseDto;
    }

    public String deleteEmployeeById(Integer id) {
        employeeRepository.deleteById(id);
        return "Delete Successful";
    }
}
