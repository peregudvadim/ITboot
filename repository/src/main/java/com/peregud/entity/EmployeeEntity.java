package com.peregud.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 40)
    private String middleName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 40, nullable = false)
    private String position;

    @ManyToMany(mappedBy = "employeeEntities")
    private Set<ProjectEntity> projectEntities = new HashSet<>();
}



