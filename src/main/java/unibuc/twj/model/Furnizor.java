package unibuc.twj.model;

public class Furnizor {

    private Integer furnizorId;
    private String numeCompanie;
    private String telefon;


    public Furnizor(Integer furnizorId, String numeCompanie, String telefon) {
        this.furnizorId = furnizorId;
        this.numeCompanie = numeCompanie;
        this.telefon = telefon;
    }

    public Furnizor(Integer furnizorId) {
        this.furnizorId = furnizorId;
    }

    public Integer getFurnizorId() {
        return furnizorId;
    }

    public void setFurnizorId(Integer furnizorId) {
        this.furnizorId = furnizorId;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void setNumeCompanie(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
