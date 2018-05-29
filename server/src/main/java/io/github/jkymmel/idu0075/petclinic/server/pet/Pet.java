package io.github.jkymmel.idu0075.petclinic.server.pet;


import io.github.jkymmel.idu0075.petclinic.server.veterinarian.Veterinarian;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String owner;
    private LocalDate birthday;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
}
