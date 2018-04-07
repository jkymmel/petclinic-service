package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veterinarian")
public class VeterinarianController {

    @Resource
    private VeterinarianService veterinarianService;

    @GetMapping
    private ResponseEntity<List<Veterinarian>> findAll() {
        return new ResponseEntity<>(veterinarianService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/:uuid")
    private ResponseEntity<Veterinarian> findByUuid(@PathVariable String uuid) {
        return veterinarianService.findByUuid(uuid)
                .map(veterinarian -> new ResponseEntity<>(veterinarian, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    private ResponseEntity<Veterinarian> save(@RequestBody Veterinarian veterinarian) {
        return new ResponseEntity<>(veterinarianService.save(veterinarian), HttpStatus.OK);
    }
}
