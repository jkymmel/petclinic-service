package io.github.jkymmel.idu0075.petclinic.server.pet;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.SavePetRequest;
import ee.ttu.idu0075._2018.ws.petclinic.wsdl.SavePetResponse;
import io.github.jkymmel.idu0075.petclinic.server.config.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Endpoint
public class PetWebService {

    private static final String NAMESPACE_URI = "http://www.ttu.ee/idu0075/2018/ws/petclinic/wsdl";

    @Resource
    private PetService petService;
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private PetModelMapper petModelMapper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SavePet")
    @ResponsePayload
    public SavePetResponse savePet(SavePetRequest savePetRequest){
        if (securityConfig.getKey().equals(savePetRequest.getApiKey())) {
            SavePetResponse response = new SavePetResponse();
            response.setPet(petModelMapper.toJaxb(petService.save(petModelMapper.toJpa(savePetRequest.getPet()))));
            return response;
        } else {
            return new SavePetResponse();
        }
    }
}
