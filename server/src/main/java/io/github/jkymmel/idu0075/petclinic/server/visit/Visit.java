package io.github.jkymmel.idu0075.petclinic.server.visit;


import io.github.jkymmel.idu0075.petclinic.server.veterinarian.Veterinarian;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Visit {
    @Id
    private String uuid;
    private String pet;
    private LocalDateTime time;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Veterinarian veterinarian;
}
