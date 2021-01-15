package unibuc.twj.model;

public class MedicamenteComanda {

    private Integer medicamenteComandaId;
    private Comanda comanda;
    private Medicamente medicamente;
    private Integer cantitate;

    public MedicamenteComanda(Integer medicamenteComandaId, Comanda comanda, Medicamente medicamente, Integer cantitate) {
        this.medicamenteComandaId = medicamenteComandaId;
        this.comanda = comanda;
        this.medicamente = medicamente;
        this.cantitate = cantitate;
    }

    public MedicamenteComanda(Integer medicamenteComandaId) {
        this.medicamenteComandaId = medicamenteComandaId;
    }

    public MedicamenteComanda(Comanda comanda, Medicamente medicamente, Integer cantitate) {
        this.comanda = comanda;
        this.medicamente = medicamente;
        this.cantitate = cantitate;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Medicamente getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(Medicamente medicamente) {
        this.medicamente = medicamente;
    }

    public Integer getMedicamenteComandaId() {
        return medicamenteComandaId;
    }

    public void setMedicamenteComandaId(Integer medicamenteComandaId) {
        this.medicamenteComandaId = medicamenteComandaId;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }
}
