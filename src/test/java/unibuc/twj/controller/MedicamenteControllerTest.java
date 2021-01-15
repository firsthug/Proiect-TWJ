package unibuc.twj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import unibuc.twj.dto.MedicamentDTO;
import unibuc.twj.exception.ComandaException;
import unibuc.twj.exception.MedicamenteException;
import unibuc.twj.model.Medicamente;
import unibuc.twj.service.MedicamenteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicamenteController.class)
public class MedicamenteControllerTest {

    @MockBean
    private MedicamenteService medicamenteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getMedicamentByIdHappyFlow() throws Exception{
        Integer id = 2;
        Medicamente medicament = new Medicamente(id, "Nuforen", 15);
        when(medicamenteService.getMedicamentById(id)).thenReturn(medicament);

        mockMvc.perform(get("/medicamente/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nume").value(medicament.getNume()))
                .andExpect(jsonPath("$.pret").value(medicament.getPret()));
    }

    @Test
    public void getMedicamentByIdSadFlow() throws Exception{
        Integer id = 2;

        when(medicamenteService.getMedicamentById(id)).thenThrow(new MedicamenteException("Medicamentul nu a fost gasit"));

        mockMvc.perform(get("/medicamente/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getMedicamenteWithFilterHappyFlow() throws Exception{
        Map<String,String> filters = new HashMap<>();
        filters.put("nume","a");
        filters.put("pretMax", "20");
        List<Medicamente> medicamenteList = new ArrayList<>();

        medicamenteList.add(new Medicamente(1, "NoSpa", 20));
        medicamenteList.add(new Medicamente(2, "Agocalmin", 15));

        when(medicamenteService.getMedicamenteWithFilters(filters)).thenReturn(medicamenteList);

        mockMvc.perform(get("/medicamente/filter" + "?nume=a&pretMax=20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nume").value(medicamenteList.get(0).getNume()))
                .andExpect(jsonPath("$[1].nume").value(medicamenteList.get(1).getNume()));
    }

    @Test
    public void getMedicamenteWithFilterSadFlow() throws Exception {

        Map<String,String> filters = new HashMap<>();
        filters.put("nume","a");
        filters.put("pretMax", "20");
        when(medicamenteService.getMedicamenteWithFilters(filters)).thenThrow(new MedicamenteException("Nu avem astfel de medicamente!"));
        mockMvc.perform(get("/medicamente/filter" + "?nume=a&pretMax=20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteMedicamentByIdHappyFlow() throws Exception {

        when(medicamenteService.deleteMedicament(1)).thenReturn("Succes!");
        mockMvc.perform(delete("/medicamente/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMedicamentByIdBadFlow() throws Exception {

        when(medicamenteService.deleteMedicament(1)).thenThrow(new MedicamenteException("Stergerea a esuat"));
        mockMvc.perform(delete("/medicamente/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void insertNewMedicamentHappyFlow() throws Exception {

        Medicamente medicament = new Medicamente(1, "Nuforen", 15);
        when(medicamenteService.insertMedicamente(any())).thenReturn(1);

        mockMvc.perform(post("/medicamente/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicament)))
                .andExpect(status().isOk());
    }

    @Test
    public void insertNewMedicamentSadFlow() throws Exception {

        Medicamente medicament = new Medicamente(1, "Nuforen", 15);
        when(medicamenteService.insertMedicamente(any())).thenThrow(new MedicamenteException("Inserarea a esuat!"));

        mockMvc.perform(post("/medicamente/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicament)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addNewMedicamentToOrderHappyFlow() throws Exception {

        Medicamente medicament = new Medicamente(1, "Nuforen", 15);
        MedicamentDTO medicamentDTO = new MedicamentDTO(medicament,5,2);
        when(medicamenteService.createComandaMedicamenteInitial(medicamentDTO)).thenReturn(3);

        mockMvc.perform(post("/medicamente/addToOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicamentDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewMedicamentToOrderSadFlow() throws Exception {

        Medicamente medicament = new Medicamente(1, "Nuforen", 15);
        MedicamentDTO medicamentDTO = new MedicamentDTO(medicament,0,2);
        when(medicamenteService.createComandaMedicamenteInitial(any())).thenThrow(new ComandaException("Nu se poate initia o comanda cu cantitate 0!"));

        mockMvc.perform(post("/medicamente/addToOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicamentDTO)))
                .andExpect(status().isBadRequest());
    }


}
