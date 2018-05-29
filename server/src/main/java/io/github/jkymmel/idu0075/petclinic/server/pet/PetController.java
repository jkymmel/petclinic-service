package io.github.jkymmel.idu0075.petclinic.server.pet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Resource
    private PetService petService;


    @GetMapping
    private ResponseEntity<List<Pet>> findAll() {
        return new ResponseEntity<>(petService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/:id")
    private ResponseEntity<Pet> findByUuid(@PathVariable Long id) {
        return petService.findById(id)
                .map(visit -> new ResponseEntity<>(visit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    private ResponseEntity<Pet> save(@RequestBody Pet pet) {
        return new ResponseEntity<>(petService.save(pet), HttpStatus.OK);
    }
}
