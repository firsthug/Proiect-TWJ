package unibuc.twj.model;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Medicamente {

    private Integer medicamentId;
    private String nume;
    private String prezentare;
    private String cantitatePerPachet;
    private String tipReteta;
    private String concentratie;
    private Furnizor furnizor;
    private Integer stoc;
    private Integer pret;

    public Medicamente()
    {}

    public Medicamente(Integer medicamentId) {
        this.medicamentId = medicamentId;
    }

    public Medicamente(Integer medicamentId, String nume, String prezentare, String cantitatePerPachet, String tipReteta, String concentratie, Furnizor furnizor, Integer stoc, Integer pret) {
        this.medicamentId = medicamentId;
        this.nume = nume;
        this.prezentare = prezentare;
        this.cantitatePerPachet = cantitatePerPachet;
        this.tipReteta = tipReteta;
        this.concentratie = concentratie;
        this.furnizor = furnizor;
        this.stoc = stoc;
        this.pret = pret;
    }

    public Medicamente(Integer medicamentId, String nume, Integer pret) {
        this.medicamentId = medicamentId;
        this.nume = nume;
        this.pret = pret;
    }

    public Integer getPret() {
        return pret;
    }

    public void setPret(Integer pret) {
        this.pret = pret;
    }

    public Furnizor getFurnizor() {
        return furnizor;
    }

    public void setFurnizor(Furnizor furnizor) {
        this.furnizor = furnizor;
    }

    public Integer getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(Integer medicamentId) {
        this.medicamentId = medicamentId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrezentare() {
        return prezentare;
    }

    public void setPrezentare(String prezentare) {
        this.prezentare = prezentare;
    }

    public String getCantitatePerPachet() {
        return cantitatePerPachet;
    }

    public void setCantitatePerPachet(String cantitatePerPachet) {
        this.cantitatePerPachet = cantitatePerPachet;
    }

    public String getTipReteta() {
        return tipReteta;
    }

    public void setTipReteta(String tipReteta) {
        this.tipReteta = tipReteta;
    }

    public String getConcentratie() {
        return concentratie;
    }

    public void setConcentratie(String concentratie) {
        this.concentratie = concentratie;
    }


    public Integer getStoc() {
        return stoc;
    }

    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nume", this.nume);
            obj.put("id", this.medicamentId);
            obj.put("prezentare", this.prezentare);
            obj.put("cantitatePerPachet", this.cantitatePerPachet);
            obj.put("tipReteta", this.tipReteta);
            obj.put("concentratie", this.concentratie);
            obj.put("furnizor", this.furnizor);
           // obj.put("pret", this.pret);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
