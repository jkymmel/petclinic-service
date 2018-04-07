package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VeterinarianService {

    @Resource
    private VeterinarianRepository veterinarianRepository;

    public Veterinarian save(Veterinarian veterinarian) {
        return veterinarianRepository.save(veterinarian);
    }

    public Optional<Veterinarian> findByUuid(String uuid) {
        return veterinarianRepository.findById(uuid);
    }

    public List<Veterinarian> findAll() {
        return (List<Veterinarian>) veterinarianRepository.findAll();
    }

}
