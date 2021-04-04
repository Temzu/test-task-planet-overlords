package com.temzu.cosmos.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "overlords")
@Data
@NoArgsConstructor
public class Overlord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Long age;

    @OneToMany(mappedBy = "overlord")
    private List<Planet> planets;
}
