package unibuc.twj.model;

public class Factura {

    private Integer facturaId;
    private Comanda comanda;
    private Double TVA;
    private Double totalFactura;

    public Factura(Comanda comanda, Double TVA, Double totalFactura) {
        this.comanda = comanda;
        this.TVA = TVA;
        this.totalFactura = totalFactura;
    }

    public Factura(Integer facturaId, Comanda comanda, Double TVA, Double totalFactura) {
        this.facturaId = facturaId;
        this.comanda = comanda;
        this.TVA = TVA;
        this.totalFactura = totalFactura;
    }

    public Factura(Comanda comanda) {
        this.comanda = comanda;
    }

    public Double getTVA() {
        return TVA;
    }

    public void setTVA(Double TVA) {
        this.TVA = TVA;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

}
