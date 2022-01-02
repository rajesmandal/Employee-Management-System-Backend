package com.example.employeeManagementSystem.expense.service;

import com.example.employeeManagementSystem.common.util.Utility;
import com.example.employeeManagementSystem.employee.service.EmployeeService;
import com.example.employeeManagementSystem.expense.data.ExpenseEntity;
import com.example.employeeManagementSystem.expense.data.ExpenseRepository;
import com.example.employeeManagementSystem.expense.web.ExpenseRequestDto;
import com.example.employeeManagementSystem.expense.web.ExpenseResponseDto;
import com.example.employeeManagementSystem.expense.web.ExpenseUpdateRequestDto;
import com.example.employeeManagementSystem.salary.data.SalaryEntity;
import com.example.employeeManagementSystem.salary.service.SalaryService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {
    private final EmployeeService employeeService;
    private final ExpenseRepository expenseRepository;
    private final SalaryService salaryService;
    Date today = Calendar.getInstance().getTime();

    public ExpenseService(EmployeeService employeeService, ExpenseRepository expenseRepository, SalaryService salaryService) {
        this.employeeService = employeeService;
        this.expenseRepository = expenseRepository;

        this.salaryService = salaryService;
    }

    public String newExpense(ExpenseRequestDto expenseRequestDto)
    {
        if(expenseRequestDto.getDate().after(today)){
            return "Invalid Date";
        }
        ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYear(expenseRequestDto.getEmpId(),
                Utility.getMonth(expenseRequestDto.getDate()),Utility.getYear(expenseRequestDto.getDate())));
        expenseEntity.setDate(expenseRequestDto.getDate());
        expenseEntity.setDescription(expenseRequestDto.getDescription());
        expenseEntity.setAmount(expenseRequestDto.getAmount());
        expenseRepository.save(expenseEntity);
        return "Expense added Successfully";
    }

    public String updateExpense(ExpenseUpdateRequestDto expenseUpdateRequestDto){
        if(expenseUpdateRequestDto.getDate().after(today)){
            return "Invalid Date";
        }
        ExpenseEntity expenseEntity = getExpenseById(expenseUpdateRequestDto.getExpenseId());
        expenseEntity.setSalaryEntity(salaryService.getSalaryByEmployeeMonthYear(expenseUpdateRequestDto.getEmpId(),
                Utility.getMonth(expenseUpdateRequestDto.getDate()),Utility.getYear(expenseUpdateRequestDto.getDate())));
        expenseEntity.setDate(expenseUpdateRequestDto.getDate());
        expenseEntity.setDescription(expenseUpdateRequestDto.getDescription());
        expenseEntity.setAmount(expenseUpdateRequestDto.getAmount());
        expenseRepository.save(expenseEntity);
        return "Expenses Updated Successfully";
    }
    public ExpenseEntity getExpenseById(Integer id){
        return expenseRepository.findById(id).get();
    }

    public List<ExpenseResponseDto> getExpenseByMonth (Integer empId, Month month, Integer year)
    {
        SalaryEntity salaryEntity = salaryService.getSalaryByEmployeeMonthYear(empId, month, year);
        List<ExpenseEntity> expenseEntityList = salaryEntity.getExpenseEntityList();
        List<ExpenseResponseDto> expenseResponseDtoList = new ArrayList<>();
        expenseEntityList.forEach(expenseEntity -> {
            expenseResponseDtoList.add(convertEntityToDto(expenseEntity));
        });

        return expenseResponseDtoList;
    }
    public Integer getTotalExpenseBySalaryId(SalaryEntity salaryEntity){
        return expenseRepository.getTotalExpenseBySalaryId(salaryEntity);
    }

    private ExpenseResponseDto convertEntityToDto(ExpenseEntity expenseEntity){
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setExpenseId(expenseEntity.getExpenseId());
        expenseResponseDto.setDate(expenseEntity.getDate());
        expenseResponseDto.setDescription(expenseEntity.getDescription());
        expenseResponseDto.setAmount(expenseEntity.getAmount());
        return expenseResponseDto;
    }


}
