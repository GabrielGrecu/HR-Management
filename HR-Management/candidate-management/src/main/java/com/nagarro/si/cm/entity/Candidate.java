package com.nagarro.si.cm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
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

    public Candidate(String username,
                     Date birthday,
                     String email,
                     String city,
                     String faculty,
                     String phoneNumber,
                     int yearsOfExperience,
                     String recruitmentChannel) {

        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.city = city;
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.recruitmentChannel = recruitmentChannel;

        this.documents = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.jobs = new HashSet<>();
    }
}
