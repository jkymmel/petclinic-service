package io.github.jkymmel.idu0075.petclinic.server.pet;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Resource
    private PetRepository petRepository;

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> findAll() {
        return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> findByVeterinarianId(Long veterinarianId) {
        return petRepository.findByVeterinarianId(veterinarianId);
    }
}
