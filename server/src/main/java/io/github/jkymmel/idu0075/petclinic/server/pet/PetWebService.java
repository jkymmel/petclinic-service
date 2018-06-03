package io.github.jkymmel.idu0075.petclinic.server.pet;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.*;
import io.github.jkymmel.idu0075.petclinic.server.config.SecurityConfig;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.Veterinarian;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.VeterinarianService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Endpoint
public class PetWebService {

    private static final String NAMESPACE_URI = "http://www.ttu.ee/idu0075/2018/ws/petclinic/wsdl";

    @Resource
    private PetService petService;
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private PetModelMapper petModelMapper;
    @Resource
    private VeterinarianService veterinarianService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllPetsRequest")
    @ResponsePayload
    public GetAllPetsResponse getAllPets(@RequestPayload GetAllPetsRequest getAllPetsRequest){
        if (securityConfig.getKey().equals(getAllPetsRequest.getApiKey())) {
            GetAllPetsResponse response = new GetAllPetsResponse();
            List<Pet> pets = petService.findAll();
            response.getPet().addAll(pets.stream().map(pet -> petModelMapper.toJaxb(pet)).collect(Collectors.toList()));
            return response;
        } else {
            return new GetAllPetsResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetPetRequest")
    @ResponsePayload
    public GetPetResponse getPet(@RequestPayload GetPetRequest getPetRequest){
        if (securityConfig.getKey().equals(getPetRequest.getApiKey())) {
            GetPetResponse response = new GetPetResponse();
            Optional<Pet> pet = petService.findById(getPetRequest.getId());
            response.setPet(pet.map(pet1 -> petModelMapper.toJaxb(pet1)).orElse(null));
            return response;
        } else {
            return new GetPetResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SavePetRequest")
    @ResponsePayload
    public SavePetResponse savePet(@RequestPayload SavePetRequest savePetRequest){
        if (securityConfig.getKey().equals(savePetRequest.getApiKey())) {
            SavePetResponse response = new SavePetResponse();
            Pet pet = petModelMapper.toJpa(savePetRequest.getPet());
            Pet savedPet = petService.save(pet);
            PetModel petModel = petModelMapper.toJaxb(savedPet);
            response.setPet(petModel);
            return response;
        } else {
            return new SavePetResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SetPetVeterinarianRequest")
    @ResponsePayload
    public SetPetVeterinarianResponse setPetVeterinarian(@RequestPayload SetPetVeterinarianRequest setPetVeterinarianRequest){
        if (securityConfig.getKey().equals(setPetVeterinarianRequest.getApiKey())) {
            SetPetVeterinarianResponse response = new SetPetVeterinarianResponse();
            Optional<Pet> optionalPet = petService.findById(setPetVeterinarianRequest.getPetId());
            Optional<Veterinarian> optionalVeterinarian = veterinarianService.findById(setPetVeterinarianRequest.getVeterinarianId());
            if (optionalPet.isPresent() && optionalVeterinarian.isPresent()) {
                Pet pet = optionalPet.get();
                pet.setVeterinarian(optionalVeterinarian.get());
                petService.save(pet);
                response.setPet(petModelMapper.toJaxb(pet));
            }
            return response;
        } else {
            return new SetPetVeterinarianResponse();
        }
    }
}
