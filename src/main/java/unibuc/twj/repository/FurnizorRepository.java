package unibuc.twj.repository;



import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FurnizorRepository {

    private JdbcTemplate jdbcTemplate;

    public FurnizorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
