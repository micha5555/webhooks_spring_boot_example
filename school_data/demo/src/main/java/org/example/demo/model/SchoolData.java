package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class SchoolData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String schoolName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolData", cascade = CascadeType.ALL)
    private List<WebhookDetails> webhookDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolData", cascade = CascadeType.ALL)
    private List<Student> students;
}
