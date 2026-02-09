package com.roima.hrms.travel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="required_documents")
public class RequiredDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_req_doc_id")
    private Long Id ;

    @ManyToOne(optional = false)
    @JoinColumn(name="fk_travel")
    private Travel travel;

    @NotBlank
    @Column(nullable = false)
    private String doc_name;

}
