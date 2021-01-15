package unibuc.twj.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.twj.dto.UserDTO;
import unibuc.twj.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public String loginUser(UserDTO userDTO) {

        String selectSQL = "SELECT * from USERS where username = ? and password = ?";
        RowMapper<User> mapper = (resultSet, i) ->
                new User(resultSet.getString("username"),
                        resultSet.getString("password"));
        List<User> userList = jdbcTemplate.query(selectSQL, mapper, userDTO.getUsername(), userDTO.getPassword());
        if (userList != null && !userList.isEmpty())
            return "Succes login!";
        else
            return "Username sau parola gresite!";
    }

    public Integer signupUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSQL ="INSERT into USERS(user_id, username, password, email, tip) values (MEDCOMSEQ.NEXTVAL,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSQL, new String[] {"USER_ID"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3,  user.getEmail());
            ps.setString(4, user.getTip());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();

    }

    public Boolean checkUsernameUnique(String username) {
        String selectSQL = "SELECT * from USERS where upper(username) = upper(?)";
        RowMapper<User> mapper = (resultSet, i) ->
                new User(resultSet.getString("username"),
                        resultSet.getString("password"));
        List<User> userList = jdbcTemplate.query(selectSQL, mapper, username);
        if (userList != null && !userList.isEmpty())
            return false;
        return true;
    }

    public Boolean checkEmailUnique(String email) {
        String selectSQL = "SELECT * from USERS where upper(email) = upper(?)";
        RowMapper<User> mapper = (resultSet, i) ->
                new User(resultSet.getString("username"),
                        resultSet.getString("password"));
        List<User> userList = jdbcTemplate.query(selectSQL, mapper, email);
        if (userList != null && !userList.isEmpty())
            return false;
        return true;
    }

    public void deleteAll() {
        String sql = "delete from users";
        jdbcTemplate.update(sql);
    }
}
