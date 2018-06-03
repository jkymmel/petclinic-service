package io.github.jkymmel.idu0075.petclinic.server.pet;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Long> {
    List<Pet> findByVeterinarianId(Long veterinarianId);
}
