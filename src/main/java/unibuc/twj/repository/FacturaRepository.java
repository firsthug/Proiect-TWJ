package unibuc.twj.repository;



import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.Factura;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class FacturaRepository {

    private JdbcTemplate jdbcTemplate;

    public FacturaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer insertFactura(Factura factura)
    {
        // la finish comanda se apeleaza
        // de facut tabel
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "INSERT into FACTURA(factura_id, id_comanda, TVA, total_factura) values (MEDCOMSEQ.NEXTVAL,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {"factura_id"});
            ps.setInt(1, factura.getComanda().getComandaId());
            ps.setDouble(2,factura.getTVA());
            ps.setDouble(3, factura.getTotalFactura());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Optional<Factura> getFacturaById(Integer id)
    {
        String selectSql = "Select * from FACTURA Where factura_id = ?";
        RowMapper<Factura> mapper = (resultSet, i) ->
                new Factura(resultSet.getInt("factura_id"),
                        new Comanda(resultSet.getInt("id_comanda")),
                        resultSet.getDouble("prezentare"),
                        resultSet.getDouble("cantitate_per_pachet"));
        List<Factura> facturaList = jdbcTemplate.query(selectSql, mapper, id);
        if (facturaList != null && !facturaList.isEmpty())
            return Optional.of(facturaList.get(0));
        else
            return Optional.empty();
    }
}
