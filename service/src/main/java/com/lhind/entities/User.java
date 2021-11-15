package com.lhind.entities;

import com.lhind.enums.Role;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "person", schema = "public")
@Data
@Where(clause = "flagDeleted=false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "flagdeleted")
    private Boolean flagDeleted;

}
