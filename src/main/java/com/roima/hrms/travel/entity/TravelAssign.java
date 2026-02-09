package com.roima.hrms.travel.entity;

import com.roima.hrms.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="travel_assigns", uniqueConstraints = @UniqueConstraint(columnNames = {"fk_user_assign","fk_travel_assign"}))
public class TravelAssign {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pk_assign_id")
    private Long Id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_user_assign")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_travel_assign")
    private Travel travel;

    @OneToMany(mappedBy = "assign", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "travelAssign",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmittedTravelDocs> documents = new ArrayList<>();
}
