package com.roima.hrms.travel.service;

import com.roima.hrms.travel.dto.ChangeExpenseStatusDto;
import com.roima.hrms.travel.dto.ExpenseCreateRequestDto;
import com.roima.hrms.travel.dto.ExpenseResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto createExpense(Long travelId,ExpenseCreateRequestDto dto, MultipartFile file);
    List<ExpenseResponseDto> getExpenseDetail(Long travelId,Long assignId);
    ExpenseResponseDto changeStatus(Long travelId, Long expenseId, ChangeExpenseStatusDto dto);
}
