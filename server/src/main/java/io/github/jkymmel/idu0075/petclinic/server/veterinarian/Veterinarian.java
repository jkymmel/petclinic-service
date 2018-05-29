package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import io.github.jkymmel.idu0075.petclinic.server.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veterinarian {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String personalCode;
    @OneToMany(mappedBy = "veterinarian")
    private List<Pet> pets;
}
