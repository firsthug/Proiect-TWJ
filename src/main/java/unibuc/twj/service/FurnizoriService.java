package unibuc.twj.service;


import org.springframework.stereotype.Service;
import unibuc.twj.repository.FurnizorRepository;

@Service
public class FurnizoriService {

    private FurnizorRepository furnizorRepository;

    public FurnizoriService(FurnizorRepository furnizorRepository) {
        this.furnizorRepository = furnizorRepository;
    }

}
