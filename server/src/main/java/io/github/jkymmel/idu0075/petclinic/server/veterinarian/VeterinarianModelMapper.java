package io.github.jkymmel.idu0075.petclinic.server.veterinarian;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.PetModel;
import ee.ttu.idu0075._2018.ws.petclinic.wsdl.VeterinarianModel;
import io.github.jkymmel.idu0075.petclinic.server.pet.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class VeterinarianModelMapper {
    public Veterinarian toJpa(VeterinarianModel veterinarianModel) {
        return Veterinarian.builder()
                .id(veterinarianModel.getId())
                .name(veterinarianModel.getName())
                .email(veterinarianModel.getEmail())
                .phoneNumber(veterinarianModel.getPhoneNumber())
                .personalCode(veterinarianModel.getPersonalCode())
                .build();
    }

    public VeterinarianModel toJaxb(Veterinarian veterinarian) {
        VeterinarianModel model = new VeterinarianModel();
        model.setEmail(veterinarian.getEmail());
        model.setId(veterinarian.getId());
        model.setName(veterinarian.getName());
        model.setPersonalCode(veterinarian.getPersonalCode());
        model.setPhoneNumber(veterinarian.getPhoneNumber());
        return model;
    }
}
