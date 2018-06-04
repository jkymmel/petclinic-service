package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.*;
import io.github.jkymmel.idu0075.petclinic.server.config.SecurityConfig;
import io.github.jkymmel.idu0075.petclinic.server.pet.Pet;
import io.github.jkymmel.idu0075.petclinic.server.pet.PetModelMapper;
import io.github.jkymmel.idu0075.petclinic.server.pet.PetService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Endpoint
public class VeterinarianWebService {

    private static final String NAMESPACE_URI = "http://www.ttu.ee/idu0075/2018/ws/petclinic/wsdl";

    @Resource
    private PetService petService;
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private VeterinarianModelMapper veterinarianModelMapper;
    @Resource
    private VeterinarianService veterinarianService;
    @Resource
    private PetModelMapper petModelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllVeterinariansRequest")
    @ResponsePayload
    public GetAllVeterinariansResponse getAllVeterinarians(@RequestPayload GetAllVeterinariansRequest getAllVeterinariansRequest){
        if (securityConfig.getKey().equals(getAllVeterinariansRequest.getApiKey())) {
            GetAllVeterinariansResponse response = new GetAllVeterinariansResponse();
            List<Veterinarian> veterinarians = veterinarianService.findAll();
            response.getVeterinarian().addAll(veterinarians.stream().map(veterinarian -> veterinarianModelMapper.toJaxb(veterinarian)).collect(Collectors.toList()));
            return response;
        } else {
            return new GetAllVeterinariansResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetVeterinarianRequest")
    @ResponsePayload
    public GetVeterinarianResponse getVeterinarian(@RequestPayload GetVeterinarianRequest getVeterinarianRequest){
        if (securityConfig.getKey().equals(getVeterinarianRequest.getApiKey())) {
            GetVeterinarianResponse response = new GetVeterinarianResponse();
            Optional<Veterinarian> veterinarian = veterinarianService.findById(getVeterinarianRequest.getId());
            response.setVeterinarian(veterinarian.map(veterinarian1 -> veterinarianModelMapper.toJaxb(veterinarian1)).orElse(null));
            return response;
        } else {
            return new GetVeterinarianResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetVeterinarianPetsRequest")
    @ResponsePayload
    public GetVeterinarianPetsResponse getVeterinarianPets(@RequestPayload GetVeterinarianPetsRequest getVeterinarianPetsRequest){
        if (securityConfig.getKey().equals(getVeterinarianPetsRequest.getApiKey())) {
            GetVeterinarianPetsResponse response = new GetVeterinarianPetsResponse();
            List<Pet> pets = petService.findByVeterinarianId(getVeterinarianPetsRequest.getVeterinarianId());
            response.getPet().addAll(pets.stream().map(pet -> petModelMapper.toJaxb(pet)).collect(Collectors.toList()));
            return response;
        } else {
            return new GetVeterinarianPetsResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveVeterinarianRequest")
    @ResponsePayload
    public SaveVeterinarianResponse saveVeterinarian(@RequestPayload SaveVeterinarianRequest saveVeterinarianRequest){
        if (securityConfig.getKey().equals(saveVeterinarianRequest.getApiKey())) {
            SaveVeterinarianResponse response = new SaveVeterinarianResponse();
            Veterinarian veterinarian = veterinarianModelMapper.toJpa(saveVeterinarianRequest.getVeterinarian());
            Veterinarian savedVeterinarian = veterinarianService.save(veterinarian);
            VeterinarianModel veterinarianModel = veterinarianModelMapper.toJaxb(savedVeterinarian);
            response.setVeterinarian(veterinarianModel);
            return response;
        } else {
            return new SaveVeterinarianResponse();
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchVeterinariansRequest")
    @ResponsePayload
    public SearchVeterinariansResponse searchVeterinarians(@RequestPayload SearchVeterinariansRequest searchVeterinariansRequest){
        if (securityConfig.getKey().equals(searchVeterinariansRequest.getApiKey())) {
            SearchVeterinariansResponse response = new SearchVeterinariansResponse();
            response.getVeterinarian().addAll(veterinarianService.findAll()
                    .stream()
                    .filter(vet -> searchVeterinariansRequest.getName() == null || vet.getName().contains(searchVeterinariansRequest.getName()))
                    .filter(vet -> searchVeterinariansRequest.getEmail() == null || vet.getEmail().contains(searchVeterinariansRequest.getEmail()))
                    .filter(vet -> searchVeterinariansRequest.getPhoneNumber() == null || vet.getPhoneNumber().contains(searchVeterinariansRequest.getPhoneNumber()))
                    .map(vet -> veterinarianModelMapper.toJaxb(vet))
                    .collect(Collectors.toList()));
            return response;
        } else {
            return new SearchVeterinariansResponse();
        }
    }
}
