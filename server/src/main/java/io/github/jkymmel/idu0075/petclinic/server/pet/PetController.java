package io.github.jkymmel.idu0075.petclinic.server.pet;

import io.github.jkymmel.idu0075.petclinic.server.veterinarian.Veterinarian;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.VeterinarianService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pet")
public class PetController {

    @Resource
    private PetService petService;

    @Resource
    private VeterinarianService veterinarianService;

    @GetMapping
    @ApiOperation("Get all pets with their veterinarians.")
    private ResponseEntity<List<Pet>> findAll() {
        return new ResponseEntity<>(petService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @ApiOperation("Find pet and it's veterinarian by pet ID.")
    private ResponseEntity<Pet> findById(@PathVariable Long id) {
        return petService.findById(id)
                .map(visit -> new ResponseEntity<>(visit, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation("Save new or existing pet.")
    private ResponseEntity<Pet> save(@RequestBody Pet pet) {
        return new ResponseEntity<>(petService.save(pet), HttpStatus.OK);
    }

    @PostMapping("{petId}/veterinarian/{veterinarianId}")
    @ApiOperation("Set pet's veterinarian by just using pet and veterinarian ID-s.")
    private ResponseEntity<Pet> setPetVeterinarian(@PathVariable Long petId, @PathVariable Long veterinarianId) {
        Optional<Pet> optionalPet = petService.findById(petId);
        Optional<Veterinarian> optionalVeterinarian = veterinarianService.findById(veterinarianId);
        if (optionalPet.isPresent() && (optionalVeterinarian.isPresent())) {
            Pet pet = optionalPet.get();
            pet.setVeterinarian(optionalVeterinarian.get());
            return new ResponseEntity<>(petService.save(pet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{petId}/veterinarian")
    @ApiOperation("Clear pet's veterinarian.")
    private ResponseEntity<Pet> clearPetVeterinarian(@PathVariable Long petId) {
        Optional<Pet> optionalPet = petService.findById(petId);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            pet.setVeterinarian(null);
            return new ResponseEntity<>(petService.save(pet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("veterinarian/{veterinarianId}")
    @ApiOperation("Get pets based on veterinarian's ID.")
    private ResponseEntity<List<Pet>> GetVeterinarianPets(@PathVariable Long veterinarianId) {
        return new ResponseEntity<>(petService.findByVeterinarianId(veterinarianId), HttpStatus.OK);
    }
}
