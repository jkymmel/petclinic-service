package io.github.jkymmel.idu0075.petclinic.server.pet;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.PetModel;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.VeterinarianModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Component
public class PetModelMapper {

    @Resource
    private PetService petService;

    @Resource
    private VeterinarianModelMapper veterinarianModelMapper;

    public Pet toJpa(PetModel petModel) {
        return Pet.builder()
                .id(petModel.getId())
                .name(petModel.getName())
                .birthday(petModel.getBirthday().toGregorianCalendar().toZonedDateTime().toLocalDate())
                .owner(petModel.getOwner())
                .veterinarian(petService.findById(petModel.getId()).orElse(new Pet()).getVeterinarian())
                .build();
    }

    public PetModel toJaxb(Pet pet) {
        PetModel model = new PetModel();
        try {
            model.setBirthday(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(GregorianCalendar
                            .from(pet.getBirthday().atStartOfDay(ZoneId.systemDefault()))));
        } catch (DatatypeConfigurationException e) {
            model.setBirthday(null);
        }
        model.setId(pet.getId());
        model.setName(pet.getName());
        model.setOwner(pet.getOwner());
        model.setVeterinarian(veterinarianModelMapper.toJaxb(pet.getVeterinarian()));
        return model;
    }
}
