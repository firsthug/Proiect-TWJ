package unibuc.twj.repository;



import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ComandaRepository {

    private JdbcTemplate jdbcTemplate;

    public ComandaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer createComanda(Comanda comanda)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "Insert into Comanda (COMANDA_ID, ID_USER, DATA_PLASARE, STATUS) values (MEDCOMSEQ.NEXTVAL,?,?,?)";
        //jdbcTemplate.update(insertSql, comanda.getComandaId(), comanda.getIdUser(), comanda.getDataPlasare(), comanda.getDataExpediere(), comanda.getStatus());
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {"COMANDA_ID"});
            ps.setInt(1, comanda.getUser().getUserId());
            ps.setDate(2, comanda.getDataPlasare());
            ps.setString(3, comanda.getStatus());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Optional<Comanda> getComandaById(Integer id)
    {
        String selectSql = "Select * from Comanda c, Users u where c.id_user = u.user_id and c.comanda_id = ?";

        RowMapper<Comanda> mapper = (resultSet, i) ->
            new Comanda(resultSet.getInt("comanda_id"),
                        new User(resultSet.getInt("id_user"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getString("tip")),
                        resultSet.getDate("Data_plasare"),
                        resultSet.getString("Status"));
        List<Comanda> comenziList = jdbcTemplate.query(selectSql, mapper, id);
        if(comenziList != null && !comenziList.isEmpty())
            return Optional.of(comenziList.get(0));
        else
            return Optional.empty();
    }

    public Integer updateComandaStatus(Integer id, String status)
    {
        String updateSql = "UPDATE COMANDA set STATUS = ? where COMANDA_ID = ?";
        return jdbcTemplate.update(updateSql, status, id);
    }

    public void updateComandaTotal(Integer id, Integer total)
    {
        String updateSql = "UPDATE COMANDA set TOTAL = ? where COMANDA_ID = ?";
        jdbcTemplate.update(updateSql, total, id);
    }

    public Optional getComandaByUserAndStatus(Integer userId, String status)
    {
        String selectSql = "SELECT * from COMANDA where ID_USER = ? and STATUS = ?";
        RowMapper<Comanda> mapper = (resultSet, i) ->
                new Comanda(resultSet.getInt("comanda_id"),
                        new User( resultSet.getInt("id_user")),
                        resultSet.getDate("data_plasare"),
                        resultSet.getString("status"));

        List<Comanda> comandaList = jdbcTemplate.query(selectSql, mapper, userId, status);
        if (comandaList != null && !comandaList.isEmpty())
            return Optional.of(comandaList.get(0));
        else
            return Optional.empty();
    }

    public Integer calculateTotal(Comanda comanda)
    {
        String selectSql = "select sum(mc.cantitate * m.PRET) as total from medicamentecomanda mc, medicamente m where mc.id_comanda = ? and mc.id_medicament = m.medicament_id";
        Integer total;
        jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(selectSql);
            ps.setInt(1, comanda.getComandaId());
            return ps;
        },(resultSet -> {
            comanda.setTotalComanda(resultSet.getInt(1));
        }));

        return comanda.getTotalComanda();
    }
}

