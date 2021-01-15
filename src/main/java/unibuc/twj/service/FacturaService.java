package unibuc.twj.service;


import org.springframework.stereotype.Service;
import unibuc.twj.exception.FacturaException;
import unibuc.twj.model.Factura;
import unibuc.twj.repository.FacturaRepository;

import java.util.Optional;

@Service
public class FacturaService {

    private FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Factura getFacturaById(Integer id)
    {
        Optional<Factura> optionalFactura = facturaRepository.getFacturaById(id);
        if(optionalFactura.isPresent()) {
            return optionalFactura.get();
        } else {
            throw new FacturaException("Factura nu a fost gasita! ");
        }
    }
}
