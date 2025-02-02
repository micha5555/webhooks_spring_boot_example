package org.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class WebhookDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String eventName;

    private String endpointUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private SchoolData schoolData;
}
