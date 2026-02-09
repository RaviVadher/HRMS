package com.roima.hrms.travel.entity;

import com.roima.hrms.travel.enums.ExpenseStatus;
import com.roima.hrms.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_expense_id")
    private Long Id;

    @NotBlank
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(nullable = false)
    private LocalDate expense_date;

    @DecimalMin(value = "0.01")
    @Column(nullable = false)
    private BigDecimal expense_amount;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus expense_status= ExpenseStatus.Draft;

    @ManyToOne(optional = false)
    @JoinColumn(name="fk_assign")
    private TravelAssign assign;

    @ManyToOne
    @JoinColumn(name = "fk_user_actionby")
    private User actionBy;

    @NotBlank
    private LocalDate submited_date;

    @NotBlank
    private LocalDate reviewed_date;

    @Column
    private String hr_remarks;

    public void approve()
    {
        this.expense_status =ExpenseStatus.Approved;
    }

}
