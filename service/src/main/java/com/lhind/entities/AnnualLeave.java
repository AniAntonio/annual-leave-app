package com.lhind.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "annual_leave", schema = "public")
@Data
public class AnnualLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    @Column(name = "remaining_days")
    private Integer remainingDays;

    @Column(name = "spent_days")
    private Integer spentDays;

    @Column(name = "employment_date")
    private Date employmentDate;
}
