package io.github.jkymmel.idu0075.petclinic.server.pet;

import ee.ttu.idu0075._2018.ws.petclinic.wsdl.PetModel;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.Veterinarian;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.VeterinarianModelMapper;
import io.github.jkymmel.idu0075.petclinic.server.veterinarian.VeterinarianService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Optional;

@Component
public class PetModelMapper {

    @Resource
    private PetService petService;

    @Resource
    private VeterinarianService veterinarianService;

    @Resource
    private VeterinarianModelMapper veterinarianModelMapper;

    public Pet toJpa(PetModel petModel) {
        Pet pet = petModel.getId() != null ? petService.findById(petModel.getId()).orElse(new Pet()) : new Pet();
        Veterinarian veterinarian = pet.getVeterinarian();
        pet.setBirthday(petModel.getBirthday() == null ? null : petModel.getBirthday().toGregorianCalendar().toZonedDateTime().toLocalDate());
        pet.setName(petModel.getName());
        pet.setOwner(petModel.getOwner());
        return pet;
    }

    public PetModel toJaxb(Pet pet) {
        PetModel model = new PetModel();
        try {
            model.setBirthday(pet.getBirthday() == null ? null : DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(GregorianCalendar
                            .from(pet.getBirthday().atStartOfDay(ZoneId.systemDefault()))));
        } catch (DatatypeConfigurationException e) {
            model.setBirthday(null);
        }
        model.setId(pet.getId());
        model.setName(pet.getName());
        model.setOwner(pet.getOwner());
        model.setVeterinarian(pet.getVeterinarian() != null ? veterinarianModelMapper.toJaxb(pet.getVeterinarian()) : null);
        return model;
    }
}
