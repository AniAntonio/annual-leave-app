package com.lhind.entities;

import com.lhind.enums.ApplicationStatus;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "application", schema = "public")
@Data
@Where(clause = "flag_deleted=false")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "days_off")
    private Integer daysOff;

    @Column(name = "comment")
    private String comment;

    @Column(name = "flag_deleted")
    private Boolean flagDeleted;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;


}
