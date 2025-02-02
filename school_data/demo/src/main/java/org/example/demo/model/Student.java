package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;
    private int age;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private SchoolData schoolData;
}
