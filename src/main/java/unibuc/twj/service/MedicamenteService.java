package unibuc.twj.service;

import org.springframework.stereotype.Service;
import unibuc.twj.dto.MedicamentDTO;
import unibuc.twj.exception.ComandaException;
import unibuc.twj.exception.MedicamenteException;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.Medicamente;
import unibuc.twj.model.MedicamenteComanda;
import unibuc.twj.model.User;
import unibuc.twj.repository.ComandaRepository;
import unibuc.twj.repository.MedicamenteComandaRepository;
import unibuc.twj.repository.MedicamenteRepository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MedicamenteService {

    private final MedicamenteRepository medicamenteRepository;

    private final ComandaRepository comandaRepository;

    private final MedicamenteComandaRepository medicamenteComandaRepository;

    public MedicamenteService(MedicamenteRepository medicamenteRepository, ComandaRepository comandaRepository, MedicamenteComandaRepository medicamenteComandaRepository) {
        this.medicamenteRepository = medicamenteRepository;
        this.comandaRepository = comandaRepository;
        this.medicamenteComandaRepository = medicamenteComandaRepository;
    }

    public void updateMedicament(Medicamente medicament)
    {
        medicamenteRepository.updateMedicament(medicament);
    }

    public Integer insertMedicamente(Medicamente medicament)
    {
       return  medicamenteRepository.createMedicament(medicament);
    }

    public String deleteMedicament(Integer id)
    {
        try {
            medicamenteRepository.deleteMedicament(id);
        }catch (Exception e)
        {
            throw new MedicamenteException("Stergerea a esuat!");
        }
        return "Succes!";
    }

    public Medicamente getMedicamentById(Integer id)
    {
        Optional<Medicamente> optionalMedicamente = medicamenteRepository.getMedicamentById(id);
        if(optionalMedicamente.isPresent()) {
            return optionalMedicamente.get();
        } else {
            throw new MedicamenteException("Medicamentul nu a fost gasit! ");
        }
    }

    public Integer createComandaMedicamenteInitial(MedicamentDTO medicamentDTO)
    {
        if(medicamentDTO.getCantitate() > medicamentDTO.getMedicamente().getStoc()) {
            throw new MedicamenteException("Stoc indisponibil!");
        }
        else {
        //try {
        Optional<Comanda> comandaOptional = comandaRepository.getComandaByUserAndStatus(medicamentDTO.getUserId(),"PRECOMANDA" );
        Comanda comanda = null;
        if(comandaOptional.isPresent()) {
            comanda = comandaOptional.get();
        }
        else {
            if(medicamentDTO.getCantitate() <= 0)
                throw new ComandaException("Nu se poate initia o comanda cu cantitate 0!");
            else {
                java.util.Date today = new java.util.Date();
                comanda = new Comanda(new User(medicamentDTO.getUserId()), new Date(today.getTime()), "PRECOMANDA");
                comanda.setComandaId(comandaRepository.createComanda(comanda));
            }
        }

        // apelam medcomanda insert sau update
        MedicamenteComanda medicamenteComanda = null;
        Optional<MedicamenteComanda> optionalMedicamenteComanda = medicamenteComandaRepository.getMedicamenteComandaByComandaAndMedicament
                                                                (comanda.getComandaId(), medicamentDTO.getMedicamente().getMedicamentId());
        if(optionalMedicamenteComanda.isPresent())
        {
            medicamenteComanda = optionalMedicamenteComanda.get();
            medicamenteComanda.setCantitate(medicamentDTO.getCantitate());
            medicamenteComandaRepository.updateMedicamenteComandaCantitate(medicamenteComanda);
        }
        else
        {
            medicamenteComanda = new MedicamenteComanda(comanda, medicamentDTO.getMedicamente(), medicamentDTO.getCantitate());
            medicamenteComandaRepository.createMedicamenteComanda(medicamenteComanda);
        }
        return comanda.getComandaId();
        }
/*
    } catch (Exception e)
    {
        System.out.println(e.getMessage() + e.getStackTrace());
        throw new ComandaException("Eroare adaugare medicament la comanda!");
    }
    */
    }

    public List<Medicamente> getMedicamenteWithFilters(Map<String, String> filters) {
        return medicamenteRepository.getAllMedicamentByFilters(filters);
    }
}
