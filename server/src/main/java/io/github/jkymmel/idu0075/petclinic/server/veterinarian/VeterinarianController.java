package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("veterinarian")
public class VeterinarianController {

    @Resource
    private VeterinarianService veterinarianService;

    @GetMapping
    @ApiOperation("Get all veterinarians.")
    private ResponseEntity<List<Veterinarian>> findAll() {
        return new ResponseEntity<>(veterinarianService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @ApiOperation("Find veterinarian by ID.")
    private ResponseEntity<Veterinarian> findById(@PathVariable Long id) {
        return veterinarianService.findById(id)
                .map(veterinarian -> new ResponseEntity<>(veterinarian, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation("Save existing or new veterinarian.")
    private ResponseEntity<Veterinarian> save(@RequestBody Veterinarian veterinarian) {
        if (veterinarian.getPersonalCode().matches("^\\d{11}$")) {
            return new ResponseEntity<>(veterinarianService.save(veterinarian), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
