package unibuc.twj.mapper;

import org.springframework.stereotype.Component;
import unibuc.twj.dto.MedicamenteComandaDTO;
import unibuc.twj.model.MedicamenteComanda;

@Component
public class MedicamenteComandaMapper {

    public MedicamenteComandaDTO toDTO(MedicamenteComanda medicamenteComanda)
    {
        MedicamenteComandaDTO medicamenteComandaDTO = new MedicamenteComandaDTO();
        medicamenteComandaDTO.setCantitate(medicamenteComanda.getCantitate());
        medicamenteComandaDTO.setMedicamentId(medicamenteComanda.getMedicamente().getMedicamentId());
        medicamenteComandaDTO.setNumeMedicament(medicamenteComanda.getMedicamente().getNume());
        medicamenteComandaDTO.setPret(medicamenteComanda.getMedicamente().getPret());
        return medicamenteComandaDTO;
    }
}
