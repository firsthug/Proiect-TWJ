package unibuc.twj.controller;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.twj.dto.ComandaDTO;
import unibuc.twj.exception.ComandaException;
import unibuc.twj.exception.MedicamenteException;
import unibuc.twj.model.Comanda;
import unibuc.twj.service.ComandaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/comanda")
@Api(value = "/comanda",
        tags = "Comanda")
public class ComandaController {

    private final ComandaService comandaService;

    public ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @PostMapping("/finish")
    @ApiOperation(value = "Finish an order",
                notes = "Update the status and total of the order received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The order was successfully updated based on the received request")
    })
    public ResponseEntity finishOrder(@Valid @RequestBody
                                          @ApiParam(name = "comanda", value = "Comanda DTO ", required = true) ComandaDTO comanda)
    {
        try {
            return ResponseEntity.ok(comandaService.placeOrder(comanda));
        }  catch (Exception e) {return ResponseEntity.badRequest().body( "Eroare finalizare comanda: " + e.getMessage()); }
    }

    @PostMapping("/cancel")
    @ApiOperation(value = "Cancel an order",
            notes = "Update the status of the order received in the request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The order was successfully updated based on the received request")
    })
    public ResponseEntity cancelOrder(@RequestBody @ApiParam(name = "destination", value = "Destination details", required = true) ComandaDTO comanda)
    {
        try {
            return ResponseEntity.ok(comandaService.cancelOrder(comanda));
        }   catch (Exception e){return ResponseEntity.badRequest().body( "Eroare comanda: " + e.getMessage()); }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obtain an order details",
            notes = "Get the order DTO based on the order id on the received request ")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The order was successfully obtained based on the received request")
    })
    public ComandaDTO getComanda(@PathVariable @ApiParam(name = "id", value = "The id of the order", required = true) Integer id) {
        return comandaService.getComanda(id);
    }

}
