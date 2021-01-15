package unibuc.twj.repository;



import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.Medicamente;
import unibuc.twj.model.MedicamenteComanda;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicamenteComandaRepository {

    private JdbcTemplate jdbcTemplate;

    public MedicamenteComandaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMedicamenteComanda(MedicamenteComanda medicamenteComanda)
    {
        // de facut sequence aici
        String insertSQL = "INSERT into MEDICAMENTECOMANDA(MEDICAMENTECOMANDA_ID,ID_COMANDA, ID_MEDICAMENT, CANTITATE) values (MEDCOMSEQ.NEXTVAL,?,?,?)";
        jdbcTemplate.update(insertSQL, medicamenteComanda.getComanda().getComandaId(),
                medicamenteComanda.getMedicamente().getMedicamentId(), medicamenteComanda.getCantitate());
    }

    public Optional getMedicamenteComandaByComandaAndMedicament(Integer idComanda, Integer idMedicament)
    {
        String selectSql = "SELECT * from MEDICAMENTECOMANDA where ID_COMANDA = ? AND ID_MEDICAMENT = ?";
        RowMapper<MedicamenteComanda> mapper = (resultSet, i) ->
                new MedicamenteComanda(resultSet.getInt("medicamentecomanda_id"),
                        new Comanda(resultSet.getInt("id_comanda")),
                        new Medicamente(resultSet.getInt("id_medicament")),
                                resultSet.getInt("cantitate"));
        List<MedicamenteComanda> medicamenteComandaList = jdbcTemplate.query(selectSql,mapper, idComanda, idMedicament);
        if (medicamenteComandaList != null && !medicamenteComandaList.isEmpty())
            return Optional.of(medicamenteComandaList.get(0));
        else
            return Optional.empty();
    }

    public void updateMedicamenteComandaCantitate(MedicamenteComanda medicamenteComanda)
    {
        String updateSql = "UPDATE MEDICAMENTECOMANDA set CANTITATE = ? WHERE MEDICAMENTECOMANDA_ID = ?";
        jdbcTemplate.update(updateSql, medicamenteComanda.getCantitate(), medicamenteComanda.getMedicamenteComandaId());
    }

    public void deleteMedicamenteComandaCantitate(Integer comandaID)
    {
        String deleteSql = "DELETE FROM MEDICAMENTECOMANDA WHERE CANTITATE = 0 AND ID_COMANDA = ?";
        jdbcTemplate.update(deleteSql, comandaID);

    }

    public void deleteAllMedicamenteComandaForComandaId(Integer comandaID)
    {
        String deleteSql = "DELETE FROM MEDICAMENTECOMANDA WHERE ID_COMANDA = ?";
        jdbcTemplate.update(deleteSql, comandaID);
    }

    public List<MedicamenteComanda> getAllMedicamentComandaByComandaId(Integer comandaID)
    {
        String selectSql = "SELECT * from MEDICAMENTECOMANDA mc, MEDICAMENTE m WHERE mc.ID_MEDICAMENT = m.MEDICAMENT_ID AND ID_COMANDA = ? ";
        RowMapper<MedicamenteComanda> mapper = (resultSet, i) ->
                new MedicamenteComanda(resultSet.getInt("medicamentecomanda_id"),
                        new Comanda(resultSet.getInt("id_comanda")),
                        new Medicamente(resultSet.getInt("id_medicament"), resultSet.getString("nume"), resultSet.getInt("pret")),
                        resultSet.getInt("cantitate"));
        return jdbcTemplate.query(selectSql,mapper, comandaID);
    }

}
