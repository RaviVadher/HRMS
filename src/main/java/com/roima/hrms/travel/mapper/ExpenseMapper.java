package com.roima.hrms.travel.mapper;

import com.roima.hrms.travel.dto.ExpenseResponseDto;
import com.roima.hrms.travel.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public static ExpenseResponseDto toDto(Expense expense) {
        ExpenseResponseDto dto = new ExpenseResponseDto();

        dto.setExpenseId(expense.getId());
        dto.setCategory(expense.getCategory());
        dto.setAmount(expense.getExpense_amount());
        dto.setExpenseDate(expense.getExpense_date());
        dto.setStatus(expense.getExpense_status());
        //dto.setReviewedDate(expense.getReviewed_date());
        //dto.setHrRemark(expense.getHr_remarks());
        //dto.setReviewerId(expense.getActionBy().getId());

        return dto;
    }
}
