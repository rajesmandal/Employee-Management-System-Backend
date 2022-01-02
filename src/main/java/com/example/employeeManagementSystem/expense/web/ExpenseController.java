package com.example.employeeManagementSystem.expense.web;

import com.example.employeeManagementSystem.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/new")
    public String expense(@RequestBody ExpenseRequestDto expenseRequestDto)
    {
        return expenseService.newExpense(expenseRequestDto);
    }

    @GetMapping(value = "/employee")
    public List<ExpenseResponseDto> getExpenseByMonth(Integer id, Month month, Integer year)
    {
        return expenseService.getExpenseByMonth(id, month, year);
    }
    @PutMapping(value = "/update")
    public String updateExpense(@RequestBody ExpenseUpdateRequestDto expenseUpdateRequestDto){
        return expenseService.updateExpense(expenseUpdateRequestDto);
    }
}
