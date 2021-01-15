package unibuc.twj.dto;

import unibuc.twj.model.Medicamente;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MedicamentDTO {

    @NotNull
    private Medicamente medicamente;
    @Min(0)
    private Integer cantitate;
    @NotNull
    private Integer userId;


    public MedicamentDTO(Medicamente medicamente, Integer cantitate, Integer userId) {
        this.medicamente = medicamente;
        this.cantitate = cantitate;
        this.userId = userId;
    }

    public Medicamente getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(Medicamente medicamente) {
        this.medicamente = medicamente;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
