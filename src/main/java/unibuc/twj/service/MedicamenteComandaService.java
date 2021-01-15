package unibuc.twj.service;


import unibuc.twj.repository.MedicamenteComandaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicamenteComandaService {

    private MedicamenteComandaRepository medicamenteComandaRepository;

    public MedicamenteComandaService(MedicamenteComandaRepository medicamenteComandaRepository) {
        this.medicamenteComandaRepository = medicamenteComandaRepository;
    }
}
