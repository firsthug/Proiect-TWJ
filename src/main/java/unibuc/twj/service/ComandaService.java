package unibuc.twj.service;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import unibuc.twj.dto.ComandaDTO;
import unibuc.twj.dto.MedicamenteComandaDTO;
import unibuc.twj.exception.ComandaException;
import unibuc.twj.exception.MedicamenteException;
import unibuc.twj.mapper.ComandaMapper;
import unibuc.twj.model.*;
import unibuc.twj.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    private final ComandaRepository  comandaRepository;
    private final MedicamenteComandaRepository medicamenteComandaRepository;
    private final MedicamenteRepository medicamenteRepository;
    private final FacturaRepository facturaRepository;
    private final UserRepository userRepository;
    private final ComandaMapper comandaMapper;

    public ComandaService(ComandaRepository comandaRepository, MedicamenteComandaRepository medicamenteComandaRepository, MedicamenteRepository medicamenteRepository, FacturaRepository facturaRepository, UserRepository userRepository, ComandaMapper comandaMapper) {
        this.comandaRepository = comandaRepository;
        this.medicamenteComandaRepository = medicamenteComandaRepository;
        this.medicamenteRepository = medicamenteRepository;
        this.facturaRepository = facturaRepository;
        this.userRepository = userRepository;
        this.comandaMapper = comandaMapper;
    }

    public ComandaDTO placeOrder(ComandaDTO comandaDTO)
    {
        medicamenteComandaRepository.deleteMedicamenteComandaCantitate(comandaDTO.getComandaId());
        //daca a pus toate val 0 in medcomanda => eroare
        List<MedicamenteComanda> medicamenteComandaList = medicamenteComandaRepository.getAllMedicamentComandaByComandaId(comandaDTO.getComandaId());
        if(medicamenteComandaList.isEmpty())
            throw new ComandaException("Nu puteti finaliza o comanda fara medicamente!");
        comandaDTO.setStatus("PLASATA");
        comandaRepository.updateComandaStatus(comandaDTO.getComandaId(), comandaDTO.getStatus());
        Optional<Comanda> optionalComanda = comandaRepository.getComandaById(comandaDTO.getComandaId());
        if(!optionalComanda.isPresent())
            throw new ComandaException("Comanda nu a fost gasita!");
        Comanda comanda = optionalComanda.get();
        Integer total = comandaRepository.calculateTotal(comanda);
        comandaDTO.setTotalComanda(total);
        comandaRepository.updateComandaTotal(comandaDTO.getComandaId(), total);
        for(MedicamenteComandaDTO mdto : comandaDTO.getMedicamenteComandaDTOS())
        {
            Optional<Medicamente> optionalMedicamente = medicamenteRepository.getMedicamentById(mdto.getMedicamentId());
            if(optionalMedicamente.isPresent()) {
                Medicamente medicament = optionalMedicamente.get();
                if(medicament.getStoc() < mdto.getCantitate())
                    throw new MedicamenteException("Medicamentul " + medicament.getMedicamentId() + " nu mai este pe stoc! Comanda nu se poate finaliza");
                else
                    medicamenteRepository.updateMedicamentStoc(medicament, mdto.getCantitate());
            }
        }
        Factura factura = new Factura(comanda);
        Double tva =  factura.getComanda().getUser().getTip().equals("PJ") ? 12.2 : 9;
        Double totalFactura = factura.getComanda().getTotalComanda() + (tva/100) * factura.getComanda().getTotalComanda();
        factura.setTVA(tva);
        factura.setTotalFactura(totalFactura);
        factura.setFacturaId(facturaRepository.insertFactura(factura));
        comandaDTO.setFacturaId(factura.getFacturaId());
        return comandaDTO;
    }

    public String cancelOrder(ComandaDTO comandaDTO)
    {
        comandaDTO.setStatus("ANULATA");
        comandaRepository.updateComandaStatus(comandaDTO.getComandaId(), comandaDTO.getStatus());
        for(MedicamenteComandaDTO mdto : comandaDTO.getMedicamenteComandaDTOS())
        {
            Optional<Medicamente> optionalMedicamente = medicamenteRepository.getMedicamentById(mdto.getMedicamentId());
            if(optionalMedicamente.isPresent())
            {
                medicamenteRepository.updateMedicamentStoc(optionalMedicamente.get(), -1*mdto.getCantitate());
            }
        }
        medicamenteComandaRepository.deleteAllMedicamenteComandaForComandaId(comandaDTO.getComandaId());
        return "Comanda a fost anulata!";
    }
    public ComandaDTO getComanda(Integer id)
    {
        Optional<Comanda> optionalComanda = comandaRepository.getComandaById(id);
        if(optionalComanda.isPresent()) {
            List<MedicamenteComanda> medicamenteComandaList = medicamenteComandaRepository.getAllMedicamentComandaByComandaId(id);
            return comandaMapper.toDTO(optionalComanda.get(), medicamenteComandaList);
        } else {
            throw new ComandaException("Comanda nu a fost gasita! ");
        }
    }
}
