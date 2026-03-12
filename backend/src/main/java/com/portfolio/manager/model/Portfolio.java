package com.portfolio.manager.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Entity representing a user's investment portfolio.
 * A portfolio is a named container (e.g. "Retirement", "Growth") owned by a user,
 * used to group and track investments (stocks, ETFs, crypto, etc.).
 */
@Entity
@Table(name = "portfolio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Portfolio name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Owner is required")
    @Column(nullable = false)
    private String owner;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
}
