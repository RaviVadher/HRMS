package com.roima.hrms.travel.repository;

import com.roima.hrms.travel.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

     List<Expense> findAllByAssign_id(Long assignId);
}