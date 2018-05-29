package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/veterinarian")
public class VeterinarianController {

    @Resource
    private VeterinarianService veterinarianService;

    @GetMapping
    private ResponseEntity<List<Veterinarian>> findAll() {
        return new ResponseEntity<>(veterinarianService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/:id")
    private ResponseEntity<Veterinarian> findByUuid(@PathVariable Long id) {
        return veterinarianService.findById(id)
                .map(veterinarian -> new ResponseEntity<>(veterinarian, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    private ResponseEntity<Veterinarian> save(@RequestBody Veterinarian veterinarian) {
        return new ResponseEntity<>(veterinarianService.save(veterinarian), HttpStatus.OK);
    }
}
