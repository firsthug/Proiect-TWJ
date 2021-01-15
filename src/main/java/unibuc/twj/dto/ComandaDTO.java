package unibuc.twj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import unibuc.twj.model.MedicamenteComanda;
import unibuc.twj.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@ApiModel(value = "Comanda DTO", description = "Details needed to communicate between requests")
public class ComandaDTO {

    @ApiModelProperty(value = "comandaId", required = true, notes = "The id of the comnada object", example = "10", position = 1)
    private Integer comandaId;
    @NotNull
    @ApiModelProperty(value = "userId", required = true, notes = "The id of the user that placed the order", example = "10", position = 2)
    private Integer userId;
    @ApiModelProperty(value = "dataPlasare", required = false, notes = "The date when the order was placed", example = "dataPlasare", position = 3)
    private Date dataPlasare;
    @NotBlank
    @ApiModelProperty(value = "status", required = true, notes = "The status of the order", example = "PLASATA", position = 4)
    private String status;
    @ApiModelProperty(value = "totalComanda", required = false, notes = "The total amount of the order", example = "400", position = 5)
    private Integer totalComanda;
    @ApiModelProperty(value = "medicamenteComandaDTOS", required = false, notes = "The ordered list of items", example = "", position = 6)
    private List<MedicamenteComandaDTO> medicamenteComandaDTOS;
    @ApiModelProperty(value = "facturaId", required = false, notes = "The id of the generated factura object ", example = "8", position = 7)
    private Integer facturaId;

    public ComandaDTO() {
    }

    public Integer getComandaId() {
        return comandaId;
    }

    public void setComandaId(Integer comandaId) {
        this.comandaId = comandaId;
    }


    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<MedicamenteComandaDTO> getMedicamenteComandaDTOS() {
        return medicamenteComandaDTOS;
    }

    public void setMedicamenteComandaDTOS(List<MedicamenteComandaDTO> medicamenteComandaDTOS) {
        this.medicamenteComandaDTOS = medicamenteComandaDTOS;
    }
}
