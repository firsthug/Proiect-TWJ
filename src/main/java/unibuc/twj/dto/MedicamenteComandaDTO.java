package unibuc.twj.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MedicamenteComandaDTO {
    private Integer medicamentId;
    private String numeMedicament;
    private Integer pret;
    private Integer cantitate;

    public MedicamenteComandaDTO() {
    }

    public Integer getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Integer medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getNumeMedicament() {
        return numeMedicament;
    }

    public void setNumeMedicament(String numeMedicament) {
        this.numeMedicament = numeMedicament;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }
}
