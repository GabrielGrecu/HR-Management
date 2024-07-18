package com.nagarro.si.cm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "candidate")
@NoArgsConstructor
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    private Date birthday;

    @Column(nullable = false, unique = true)
    private String email;

    private String city;

    private String faculty;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "years_experience")
    private int yearsOfExperience;

    @Column(name = "recruitment_channel")
    private String recruitmentChannel;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Document> documents;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Feedback> feedbackList;

    @ManyToMany
    @JoinTable(name = "candidate_job",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> jobs;

}
