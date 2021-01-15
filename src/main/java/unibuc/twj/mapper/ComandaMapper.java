package unibuc.twj.mapper;

import org.springframework.stereotype.Component;
import unibuc.twj.dto.ComandaDTO;
import unibuc.twj.dto.MedicamenteComandaDTO;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.MedicamenteComanda;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComandaMapper {

    private final MedicamenteComandaMapper medicamenteComandaMapper;

    public ComandaMapper(MedicamenteComandaMapper medicamenteComandaMapper) {
        this.medicamenteComandaMapper = medicamenteComandaMapper;
    }

    public ComandaDTO toDTO(Comanda comanda, List<MedicamenteComanda> medicamenteComandaList) {
        ComandaDTO comandaDTO = new ComandaDTO();
        comandaDTO.setComandaId(comanda.getComandaId());
        comandaDTO.setDataPlasare(comanda.getDataPlasare());
        comandaDTO.setStatus(comanda.getStatus());
        comandaDTO.setTotalComanda(comanda.getTotalComanda());
        comandaDTO.setUserId(comanda.getUser().getUserId());

        List<MedicamenteComandaDTO> medicamenteComandaDTOS = medicamenteComandaList.stream()
                .map(i -> medicamenteComandaMapper.toDTO(i))
                .collect(Collectors.toList());
        comandaDTO.setMedicamenteComandaDTOS(medicamenteComandaDTOS);
        return comandaDTO;
    }
}
