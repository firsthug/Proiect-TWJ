package unibuc.twj.model;


import java.sql.Date;

public class Comanda {

    private Integer comandaId;
    private User user;
    private Date dataPlasare;
    private String status;
    private Integer totalComanda;

    public Comanda(Integer comandaId) {
        this.comandaId = comandaId;
    }

    public Comanda(Integer comandaId, User user, Date dataPlasare, String status) {
        this.comandaId = comandaId;
        this.user = user;
        this.dataPlasare = dataPlasare;
        this.status = status;
    }

    public Comanda(User user, Date dataPlasare, String status) {
        this.user = user;
        this.dataPlasare = dataPlasare;
        this.status = status;
    }

    public Comanda(Integer comandaId, User user, Date dataPlasare, String status, Integer totalComanda) {
        this.comandaId = comandaId;
        this.user = user;
        this.dataPlasare = dataPlasare;
        this.status = status;
        this.totalComanda = totalComanda;
    }

    public Integer getComandaId() {
        return comandaId;
    }

    public void setComandaId(Integer comandaId) {
        this.comandaId = comandaId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDataPlasare() {
        return dataPlasare;
    }

    public void setDataPlasare(Date dataPlasare) {
        this.dataPlasare = dataPlasare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(Integer totalComanda) {
        this.totalComanda = totalComanda;
    }
}
