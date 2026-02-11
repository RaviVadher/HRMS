package com.roima.hrms.travel.controller;

import com.roima.hrms.travel.dto.ChangeExpenseStatusDto;
import com.roima.hrms.travel.dto.ExpenseCreateRequestDto;
import com.roima.hrms.travel.dto.ExpenseResponseDto;
import com.roima.hrms.travel.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/travels/{travel_id}/assign")
public class ExpenseController {

   private ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/expense",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ExpenseResponseDto createExpense(
            @PathVariable Long travel_id,
             ExpenseCreateRequestDto dto,
            @RequestParam MultipartFile file
    ) {
        System.out.println("request enter");
        return expenseService.createExpense(travel_id, dto, file);
    }


    @GetMapping("{assign_id}/expense")
    @PreAuthorize("hasRole('Employee')")
    public List<ExpenseResponseDto> getExpense(@PathVariable Long travel_id,@PathVariable Long assign_id ){

        return expenseService.getExpenseDetail(travel_id,assign_id);
    }


    @PostMapping("expense/{expense_id}/changestatus")
    @PreAuthorize("hasRole('Hr')")
    public ExpenseResponseDto changeStatus(@PathVariable Long travel_id, @PathVariable Long expense_id, ChangeExpenseStatusDto dto){

        return expenseService.changeStatus(travel_id,expense_id,dto);

    }
}
