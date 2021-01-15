package unibuc.twj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.twj.dto.MedicamentDTO;
import unibuc.twj.exception.MedicamenteException;
import unibuc.twj.model.Medicamente;
import unibuc.twj.service.MedicamenteService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/medicamente")
public class MedicamenteController {

    private final MedicamenteService medicamenteService;

    public MedicamenteController(MedicamenteService medicamenteService) {
        this.medicamenteService = medicamenteService;
    }

    @PostMapping("/insert")
    public ResponseEntity createNewMedicament(@RequestBody Medicamente medicament) {
        try {
            return ResponseEntity.ok(medicamenteService.insertMedicamente(medicament));
        }   catch (MedicamenteException e){return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @GetMapping("/{id}")
    public Medicamente getMedicamentById(@PathVariable Integer id) {
        return medicamenteService.getMedicamentById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMedicament(@PathVariable Integer id)
    {
        try {
            return ResponseEntity.ok(medicamenteService.deleteMedicament(id));
        }   catch (MedicamenteException e){return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @PostMapping("/addToOrder")
    public ResponseEntity addMedicamentToOrder(@Valid @RequestBody MedicamentDTO medicamentDTO) {
        try {
            return ResponseEntity.ok(medicamenteService.createComandaMedicamenteInitial(medicamentDTO));
        }
        catch (Exception e){return ResponseEntity.badRequest().body(e.getMessage());}
    }

    @GetMapping("/filter")
    public ResponseEntity getMedicamenteWithFilters(@RequestParam Map<String,String> filters)
    {
        try
        {
            return ResponseEntity.ok(medicamenteService.getMedicamenteWithFilters(filters));
        }
        catch (Exception e){return ResponseEntity.badRequest().body(e.getMessage());}
    }


}
