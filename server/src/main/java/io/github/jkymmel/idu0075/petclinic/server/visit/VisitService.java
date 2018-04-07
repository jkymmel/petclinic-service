package io.github.jkymmel.idu0075.petclinic.server.visit;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VisitService {

    @Resource
    private VisitRepository visitRepository;

    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    public Optional<Visit> findByUuid(String uuid) {
        return visitRepository.findById(uuid);
    }

    public List<Visit> findAll() {
        return (List<Visit>) visitRepository.findAll();
    }
}
