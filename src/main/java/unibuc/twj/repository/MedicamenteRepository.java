package unibuc.twj.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.twj.model.Comanda;
import unibuc.twj.model.Furnizor;
import unibuc.twj.model.Medicamente;
import unibuc.twj.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class MedicamenteRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicamenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer createMedicament(Medicamente medicamente)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "Insert into MEDICAMENTE(MEDICAMENT_ID, NUME, PREZENTARE, TIP_RETETA, CANTITATE_PER_PACHET, CONCENTRATIE, ID_FURNIZOR, STOC, PRET) values (?,?,?,?,?,?,?,?,?)";
        //jdbcTemplate.update(insertSql,medicamente.getMedicamentId(), medicamente.getNume(), medicamente.getPrezentare(), medicamente.getTipReteta(),
                //medicamente.getCantitatePerPachet(), medicamente.getConcentratie(), medicamente.getFurnizor().getFurnizorId(), medicamente.getStoc(), medicamente.getPret());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[] {"MEDICAMENT_ID"});
            ps.setInt(1, medicamente.getMedicamentId());
            ps.setString(2, medicamente.getNume());
            ps.setString(3, medicamente.getPrezentare());
            ps.setString(4,  medicamente.getTipReteta());
            ps.setString(5, medicamente.getCantitatePerPachet());
            ps.setString(6, medicamente.getConcentratie());
            ps.setInt(7,  medicamente.getFurnizor().getFurnizorId());
            ps.setInt(8,  medicamente.getStoc());
            ps.setInt(9, medicamente.getPret());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void deleteMedicament(Integer medicamentId)
    {
        String deleteSql = "Delete from MEDICAMENTE WHERE medicament_id = ?";
        jdbcTemplate.update(deleteSql, medicamentId);
    }

    public void updateMedicament(Medicamente medicamente)
    {
        String updateSql = "Update MEDICAMENTE set NUME = ?, PREZENTARE = ?, TIP_RETETA = ?, CANTITATE_PER_PACHET =? , " +
                "CONCENTARTIE = ?, ID_FURNIZOR = ?, PRET = ? where MEDICAMENT_ID = ?";
        jdbcTemplate.update(updateSql, medicamente.getNume(), medicamente.getPrezentare(), medicamente.getTipReteta(),
                medicamente.getCantitatePerPachet(), medicamente.getConcentratie(), medicamente.getFurnizor().getFurnizorId(),
                medicamente.getPret(),  medicamente.getMedicamentId());
    }


    public Optional<Medicamente> getMedicamentById(Integer id)
    {
        String selectSql = "Select * from MEDICAMENTE Where medicament_id = ?";
        RowMapper<Medicamente> mapper = (resultSet, i) ->
                new Medicamente(resultSet.getInt("medicament_id"),
                                resultSet.getString("nume"),
                        resultSet.getString("prezentare"),
                        resultSet.getString("cantitate_per_pachet"),
                        resultSet.getString("tip_reteta"),
                        resultSet.getString("concentratie"),
                        new Furnizor( resultSet.getInt("id_furnizor")),
                        resultSet.getInt("stoc"),
                        resultSet.getInt("pret"));
            List<Medicamente> medicamenteList = jdbcTemplate.query(selectSql, mapper, id);
            if (medicamenteList != null && !medicamenteList.isEmpty())
                return Optional.of(medicamenteList.get(0));
            else
                return Optional.empty();
    }

    public Integer updateMedicamentStoc(Medicamente medicamente, Integer quantity)
    {
        String sql = "UPDATE MEDICAMENTE set STOC = ? WHERE MEDICAMENT_ID = ?";
        return jdbcTemplate.update(sql, medicamente.getStoc() - quantity, medicamente.getMedicamentId());
    }

    public List<Medicamente> getAllMedicamentByFilters(Map<String, String> filters) {
        StringBuilder selectSql = new StringBuilder("SELECT * from MEDICAMENTE WHERE 1=1 ");
        Integer position = 1;
        List<FilterObject> filterObjects = new ArrayList<FilterObject>();
        if(!filters.isEmpty()) {
            for (String filterKey : filters.keySet()) {
                if (filterKey.equals("nume")) {
                    selectSql.append(" and upper(nume) like upper(?) ");
                } else if (filterKey.equals("concentratie")) {
                    selectSql.append(" and upper(concentratie) like upper(?) ");
                } else if (filterKey.equals("tipReteta")) {
                    selectSql.append(" and upper(tip_reteta) like upper(?) ");
                } else if (filterKey.equals("pretMin")) {
                    selectSql.append(" and pret >= ? ");
                } else if (filterKey.equals("pretMax")) {
                    selectSql.append(" and pret <= ? ");
                }
                FilterObject f = new FilterObject(filterKey, filters.get(filterKey), position);
                filterObjects.add(f);
                position++;
            }

            Collections.sort(filterObjects, new Comparator<FilterObject>() {
                @Override
                public int compare(FilterObject o1, FilterObject o2) {
                    return o1.position < o2.position ? -1 : o1.position > o2.position ? 1 : 0;
                }
            });

            return jdbcTemplate.query(connection -> {
                PreparedStatement ps = connection.prepareStatement(selectSql.toString());
                for(FilterObject f : filterObjects)
                {
                    if(f.keyName.contains("pret"))
                        ps.setInt(f.position, Integer.valueOf(f.value));
                    else
                        ps.setString(f.position, '%' + f.value + '%');
                }
                return ps;
            },(resultSet, i) -> {
                Medicamente m = new Medicamente(resultSet.getInt("medicament_id"),
                        resultSet.getString("nume"),
                        resultSet.getString("prezentare"),
                        resultSet.getString("cantitate_per_pachet"),
                        resultSet.getString("tip_reteta"),
                        resultSet.getString("concentratie"),
                        new Furnizor( resultSet.getInt("id_furnizor")),
                        resultSet.getInt("stoc"),
                        resultSet.getInt("pret"));
                return m;
            });
        }
        else
        {
            RowMapper<Medicamente> mapper = (resultSet, i) ->
            new Medicamente(resultSet.getInt("medicament_id"),
            resultSet.getString("nume"),
            resultSet.getString("prezentare"),
            resultSet.getString("cantitate_per_pachet"),
            resultSet.getString("tip_reteta"),
            resultSet.getString("concentratie"),
            new Furnizor( resultSet.getInt("id_furnizor")),
            resultSet.getInt("stoc"),
            resultSet.getInt("pret"));
            return jdbcTemplate.query(selectSql.toString(), mapper);
        }
    }

     class FilterObject
    {
        private String keyName;
        private String value;
        private Integer position;

        public FilterObject(String keyName, String value, Integer position) {
            this.keyName = keyName;
            this.value = value;
            this.position = position;
        }
    }
}
