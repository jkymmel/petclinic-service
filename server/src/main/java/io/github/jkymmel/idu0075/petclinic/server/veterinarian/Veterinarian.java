package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import io.github.jkymmel.idu0075.petclinic.server.visit.Visit;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uuid;
    private String name;
    private String email;
    private String phoneNumber;
    @OneToMany(mappedBy = "veterinarian")
    private List<Visit> visits;
}
